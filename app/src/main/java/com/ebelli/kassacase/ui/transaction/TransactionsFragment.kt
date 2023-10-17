package com.ebelli.kassacase.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ebelli.kassacase.R
import com.ebelli.kassacase.databinding.FragmentTransactionsBinding
import com.ebelli.kassacase.ui.component.BaseVerticalDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private val transactionViewModel: TransactionViewModel by viewModels()

    private val transactionAdapter = TransactionAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        transactionViewModel.fetchTransactions()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionViewModel.transactionViewState.collect { viewState ->
                    viewState.transactionEntities?.takeIf { it.isEmpty().not() }
                        ?.let { safeTransactions ->
                            transactionAdapter.submitList(safeTransactions)
                            binding.isEmpty = false
                        } ?: kotlin.run {
                        binding.isEmpty = true
                    }

                    binding.isLoading = viewState.isLoading
                }
            }
        }
    }

    private fun initViews() = with(binding) {
        binding.rvTransactions.adapter = transactionAdapter
        rvTransactions.addItemDecoration(
            BaseVerticalDividerItemDecoration(
                requireContext(),
                paddingOutResId = R.dimen.margin_10,
                paddingInResId = R.dimen.margin_10
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}