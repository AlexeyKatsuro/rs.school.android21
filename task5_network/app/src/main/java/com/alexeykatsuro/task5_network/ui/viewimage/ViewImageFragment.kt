package com.alexeykatsuro.task5_network.ui.viewimage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.alexeykatsuro.task5_network.R
import com.alexeykatsuro.task5_network.databinding.VeiwImageFragmentBinding

class ViewImageFragment : Fragment(R.layout.veiw_image_fragment) {

    private val viewBinding by viewBinding(VeiwImageFragmentBinding::bind)

    private val args: ViewImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.also { ui ->
            ui.image.load(args.url)
        }
    }
}