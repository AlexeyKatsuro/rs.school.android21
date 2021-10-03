package com.alexeykatsuro.task5_network.utils
import androidx.fragment.app.Fragment
import com.alexeykatsuro.task5_network.App
import com.alexeykatsuro.task5_network.MainActivity


fun Fragment.requireMainActivity() = requireActivity() as MainActivity

fun Fragment.requireApp() = requireActivity().application as App