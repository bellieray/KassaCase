package com.ebelli.kassacase.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebelli.kassacase.data.repository.CurrencyRepository
import com.ebelli.kassacase.model.TransactionEntity
import com.ebelli.kassacase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(val currencyRepository: CurrencyRepository) :
    ViewModel() {
    private val _transactionViewState = MutableStateFlow(TransactionViewState())
    val transactionViewState = _transactionViewState.asStateFlow()

    fun fetchTransactions() {
        _transactionViewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = currencyRepository.getAllTransactions()) {
                is NetworkResult.Success -> {
                    _transactionViewState.update {
                        it.copy(
                            transactionEntities = response.data,
                            isLoading = false
                        )
                    }
                }
                is NetworkResult.Failed -> {
                    _transactionViewState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}

data class TransactionViewState(
    val isLoading: Boolean = false,
    val transactionEntities: List<TransactionEntity>? = null
)