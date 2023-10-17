package com.ebelli.kassacase.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebelli.kassacase.databinding.RowTransactionBinding
import com.ebelli.kassacase.model.TransactionEntity

class TransactionAdapter() :
    ListAdapter<TransactionEntity, RecyclerView.ViewHolder>(HomeCurrencyDiffCallback) {
    object HomeCurrencyDiffCallback : DiffUtil.ItemCallback<TransactionEntity>() {
        override fun areItemsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
            return oldItem.currency.balance == newItem.currency.balance
        }

        override fun areContentsTheSame(oldItem: TransactionEntity, newItem: TransactionEntity): Boolean {
            return oldItem.purchasedQty == newItem.purchasedQty && oldItem.currency.balance == newItem.currency.balance
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeTransactionViewHolder(
            RowTransactionBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return if (holder is HomeTransactionViewHolder) holder.bind(getItem(position)) else return
    }

    inner class HomeTransactionViewHolder(private val binding: RowTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionEntity: TransactionEntity) {
            binding.transaction = transactionEntity
            binding.ivCurrency.setImageResource(transactionEntity.currency.imageRes)
        }
    }
}