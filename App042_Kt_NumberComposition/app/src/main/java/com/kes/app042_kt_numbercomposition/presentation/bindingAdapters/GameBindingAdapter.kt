package com.kes.app042_kt_numbercomposition.presentation.bindingAdapters

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kes.app042_kt_numbercomposition.R

interface OnOptionClickListener {
    operator fun invoke(option: Int)
}

@BindingAdapter("textFromInt")
fun bindTextFromInt(view: TextView, number: Int) {
    view.text = number.toString()
}

@BindingAdapter("progressBarTint")
fun bindProgressBarTint(view: ProgressBar, enoughPercent: Boolean) {
    view.apply {
        progressTintList = getProgressColor(context, enoughPercent)
    }
}

@BindingAdapter("progressTextTint")
fun bindProgressTextTint(view: TextView, enoughAnswers: Boolean) {
    view.apply {
        setTextColor(getProgressColor(context, enoughAnswers))
    }
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(view: TextView, clickListener: OnOptionClickListener) {
    view.setOnClickListener {
        clickListener(view.text.toString().toInt())
    }
}

private fun getProgressColor(context: Context, enough: Boolean): ColorStateList {
    val colorId = if (enough) R.color.green
    else R.color.red
    return ColorStateList.valueOf(context.resources.getColor(colorId, null))
}