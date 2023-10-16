package com.ebelli.kassacase.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebelli.kassacase.data.preferences.Prefs
import com.ebelli.kassacase.data.repository.CurrencyRepository
import com.ebelli.kassacase.model.Currency
import com.ebelli.kassacase.utils.CURRENT_BALANCE
import com.ebelli.kassacase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val STATIC_ADDING_BALANCE = 100.0

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CurrencyRepository, val prefs: Prefs
) : ViewModel() {
    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState: StateFlow<HomeViewState> = _homeViewState.asStateFlow()

    init {
        getCurrencies()
        getCurrentBalance()
    }

    fun getCurrentBalance() {
        viewModelScope.launch {
            prefs.getSharedDouble(CURRENT_BALANCE).collect { balance ->
                _homeViewState.update {
                    it.copy(
                        currentBalance = balance
                    )
                }
            }
        }
    }

    fun updateCurrentBalance(isShouldBeDecreasing: Boolean, value: Double = STATIC_ADDING_BALANCE) {
        val currentBalance = _homeViewState.value.currentBalance
        viewModelScope.launch {
            if (currentBalance != null) {
                if (isShouldBeDecreasing)
                    prefs.setSharedDouble(
                        CURRENT_BALANCE,
                        currentBalance - value
                    ) else prefs.setSharedDouble(CURRENT_BALANCE, currentBalance + value)
            }
        }
        _homeViewState.update { it.copy(viewEvents = addEventToList(HomeViewEvent.BalanceUpdated)) }
    }

    private fun getCurrencies() {
        if (_homeViewState.value.currencies != null) return
        _homeViewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = repository.getCurrencies()) {
                is NetworkResult.Success -> {
                    _homeViewState.update {
                        it.copy(
                            currencies = response.data?.data?.map { data ->
                                Currency(data.key, data.value)
                            }, isLoading = false
                        )
                    }
                }

                is NetworkResult.Failed -> {
                    _homeViewState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun addEventToList(viewEvent: HomeViewEvent): List<HomeViewEvent> {
        val eventList = _homeViewState.value.viewEvents?.toMutableList() ?: mutableListOf()
        eventList.add(viewEvent)
        return eventList
    }

    fun eventConsumed(viewEvent: HomeViewEvent.BalanceUpdated) {
        _homeViewState.update { currentUiState ->
            val newViewEventList =
                currentUiState.viewEvents?.filterNot { it == viewEvent } ?: mutableListOf()
            currentUiState.copy(viewEvents = newViewEventList)
        }
    }
}

data class HomeViewState(
    val isLoading: Boolean = false,
    val currencies: List<Currency>? = null,
    val currentBalance: Double? = null,
    val viewEvents: List<HomeViewEvent>? = null,
)

sealed class HomeViewEvent {
    object BalanceUpdated : HomeViewEvent()
}