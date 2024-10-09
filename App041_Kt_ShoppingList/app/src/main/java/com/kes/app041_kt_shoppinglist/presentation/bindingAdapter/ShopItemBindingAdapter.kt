package com.kes.app041_kt_shoppinglist.presentation.bindingAdapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

private const val ERROR_MESSAGE_NAME = "Incorrect name"
private const val ERROR_MESSAGE_COUNT = "Incorrect count"

@BindingAdapter("errorInputName")
fun bindErrorInputName(view: TextInputLayout, isError: Boolean) {
    when (isError) {
        true -> view.error = ERROR_MESSAGE_NAME
        else -> view.error = null
    }
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(view: TextInputLayout, isError: Boolean) {
    when (isError) {
        true -> view.error = ERROR_MESSAGE_COUNT
        else -> view.error = null
    }
}