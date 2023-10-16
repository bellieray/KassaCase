package com.ebelli.kassacase.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebelli.kassacase.databinding.RowHomeCurrencyBinding
import com.ebelli.kassacase.model.Currency

class HomeCurrencyAdapter(val onItemClicked : (Currency) -> Unit) :
    ListAdapter<Currency, RecyclerView.ViewHolder>(HomeCurrencyDiffCallback) {
    object HomeCurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.balance == newItem.balance
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.balance == newItem.balance
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeCurrencyViewHolder(RowHomeCurrencyBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return if (holder is HomeCurrencyViewHolder) holder.bind(getItem(position)) else return
    }

    inner class HomeCurrencyViewHolder(private val binding: RowHomeCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currency) {
            binding.currency = currency
            binding.ivCurrency.setImageResource(currency.imageRes)
            binding.tvPurchase.setOnClickListener {
                onItemClicked.invoke(currency)
            }
        }
    }
}