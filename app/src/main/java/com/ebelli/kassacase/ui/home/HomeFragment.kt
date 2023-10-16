package com.ebelli.kassacase.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ebelli.kassacase.R
import com.ebelli.kassacase.databinding.FragmentHomeBinding
import com.ebelli.kassacase.model.Currency
import com.ebelli.kassacase.ui.component.BaseVerticalDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private val homeAdapter = HomeCurrencyAdapter(this::showPurchaseDialog)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeViewState.collect { viewState ->
                    viewState.currencies?.let { currencies ->
                        homeAdapter.submitList(currencies)
                    }

                    binding.isLoading = viewState.isLoading

                    viewState.currentBalance?.let {
                        binding.currentBalance = it
                    }

                    viewState.viewEvents?.firstOrNull()?.let { homeViewEvent ->
                        when (homeViewEvent) {
                            is HomeViewEvent.BalanceUpdated -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Cüzdan Güncellendi",
                                    Toast.LENGTH_LONG
                                ).show()

                                homeViewModel.getCurrentBalance()
                            }
                        }
                        homeViewModel.eventConsumed(homeViewEvent)
                    }
                }
            }
        }
    }

    private fun initViews() = with(binding) {
        rvCurrencies.adapter = homeAdapter
        rvCurrencies.addItemDecoration(
            BaseVerticalDividerItemDecoration(
                requireContext(),
                paddingOutResId = R.dimen.margin_10,
                paddingInResId = R.dimen.margin_10
            )
        )

        tvAddToBalance.setOnClickListener {
            homeViewModel.updateCurrentBalance(false)
        }
    }

    private fun comparePrice(totalPurchase: Double) {
        homeViewModel.homeViewState.value.currentBalance.takeIf { it != null && it > 0 && it >= totalPurchase }
            ?.let {
                homeViewModel.updateCurrentBalance(true, totalPurchase)
            } ?: kotlin.run {
            Toast.makeText(requireContext(), "Yetersiz Bakiye", Toast.LENGTH_LONG).show()
        }
    }

    private fun showPurchaseDialog(currency: Currency) {
        val dialog = HomePurchaseDialog(currency, ::comparePrice)
        dialog.show(childFragmentManager, HomePurchaseDialog::class.java.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}