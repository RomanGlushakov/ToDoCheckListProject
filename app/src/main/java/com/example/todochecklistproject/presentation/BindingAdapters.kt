package com.example.todochecklistproject.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter ("inputErrorName")
fun bindInputErrorName(til: TextInputLayout, isError: Boolean) {

    val message = if (isError) {
        "Поле не заполнено"
    } else {
        null
    }
    til.error = message
}