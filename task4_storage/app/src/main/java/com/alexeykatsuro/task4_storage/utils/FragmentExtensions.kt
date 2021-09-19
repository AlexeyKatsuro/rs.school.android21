package com.alexeykatsuro.task4_storage.utils

import androidx.fragment.app.Fragment
import com.alexeykatsuro.task4_storage.App
import com.alexeykatsuro.task4_storage.MainActivity
import com.alexeykatsuro.task4_storage.SimpleNavigator

fun Fragment.requireMainActivity() = requireActivity() as MainActivity

fun Fragment.requireApp() = requireActivity().application as App

val Fragment.navigator: SimpleNavigator
    get() = requireMainActivity()