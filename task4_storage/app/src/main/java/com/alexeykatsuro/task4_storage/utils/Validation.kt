package com.alexeykatsuro.task4_storage.utils

import com.google.android.material.textfield.TextInputLayout

// File contains some useful extensions utility methods for validation under [TextInputLayout]

typealias InputValidator = (text: String) -> String?
typealias Form = Map<TextInputLayout, List<InputValidator>>


fun TextInputLayout.validate(validators: List<InputValidator>): Boolean {
    val text = this.editText?.text?.toString() ?: ""
    for (validator in validators) {
        val errorMessage = validator(text)
        if (errorMessage != null) {
            this.error = errorMessage
            return false
        }
    }
    this.error = null
    this.isErrorEnabled = false
    return true
}

fun Form.validate(): Boolean = entries.fold(true, { isValid, entry ->
    val (input, validators) = entry
    input.validate(validators) && isValid
})