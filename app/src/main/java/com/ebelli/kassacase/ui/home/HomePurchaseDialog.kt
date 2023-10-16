package com.ebelli.kassacase.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.ebelli.kassacase.R
import com.ebelli.kassacase.databinding.DialogHomePurchaseBinding
import com.ebelli.kassacase.model.Currency
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomePurchaseDialog(
    val currency: Currency,
    val onPurchaseClicked: (totalPurchase: Double) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: DialogHomePurchaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogHomePurchaseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            ((it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).let { bottomSheet ->
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        binding.tvPurchase.setOnClickListener {
            dialog?.dismiss()
            tvPurchaseBalance.text.toString().takeIf { it.isBlank().not() }?.let { value ->
                onPurchaseClicked.invoke(value.replace(',', '.').toDouble())
            }
        }
        currency = this@HomePurchaseDialog.currency
        ivCurrency.setImageResource(this@HomePurchaseDialog.currency.imageRes)
        val signRes =
            ContextCompat.getDrawable(requireContext(), this@HomePurchaseDialog.currency.signRes)
        signRes?.setBounds(0, 0, 80, 80)
        etPurchaseQty.setCompoundDrawables(
            null,
            null,
            signRes,
            null
        )

        val tlSign = ContextCompat.getDrawable(requireContext(), R.drawable.ic_tl)
        tlSign?.setBounds(0, 0, 80, 80)
        tvPurchaseBalance.setCompoundDrawables(
            null,
            null,
            tlSign,
            null
        )

        etPurchaseQty.doOnTextChanged { text, start, before, count ->
            val newBalance = currency?.newBalance
            val inputText = text.toString()

            if (inputText.isNotEmpty() && newBalance != null) {
                val intValue =
                    inputText.toIntOrNull() // Convert the input text to an integer or null if it's not a valid integer.
                if (intValue != null) {
                    val result = intValue * newBalance
                    tvPurchaseBalance.text = String.format("%.2f", result)
                } else {
                    // Handle the case where the input is not a valid integer
                    // You can show an error message or perform other actions here.
                    tvPurchaseBalance.text = ""
                }
            } else {
                // Handle the case where either the input or newBalance is missing or empty
                // You can clear the TextView or perform other actions here.
                tvPurchaseBalance.text = ""
            }
        }
    }
}