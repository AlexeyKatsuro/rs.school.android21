package com.alexeykatsuro.tast5_network.utils
import androidx.fragment.app.Fragment
import com.alexeykatsuro.tast5_network.App
import com.alexeykatsuro.tast5_network.MainActivity


fun Fragment.requireMainActivity() = requireActivity() as MainActivity

fun Fragment.requireApp() = requireActivity().application as App