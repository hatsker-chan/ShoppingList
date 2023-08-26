package com.example.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(til: TextInputLayout, errorInput: Boolean){
    if (errorInput) {
        til.error = "Incorrect count input"
    } else {
        til.error = null
    }
}

@BindingAdapter("errorInputName")
fun bindErrorInputName(til: TextInputLayout, errorInput: Boolean){
    if (errorInput) {
        til.error = "Incorrect name input"
    } else {
        til.error = null
    }
}