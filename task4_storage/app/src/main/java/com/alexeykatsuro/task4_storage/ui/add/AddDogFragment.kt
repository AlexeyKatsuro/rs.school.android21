package com.alexeykatsuro.task4_storage.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alexeykatsuro.task4_storage.R
import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.databinding.AddAnimalFragmentBinding
import com.alexeykatsuro.task4_storage.utils.*


class AddAnimalFragment : Fragment(R.layout.add_animal_fragment) {
    companion object {

        fun createEditAnimalFragment(animal: Animal): AddAnimalFragment {
            return AddAnimalFragment().apply {
                arguments = animal.toBundle()
            }
        }
    }

    private val animalToUpdate by lazy { Animal.fromBundle(arguments) }

    private val isAddScreen: Boolean
        get() = animalToUpdate == null

    private val viewBinding: AddAnimalFragmentBinding by viewBinding(AddAnimalFragmentBinding::bind)

    private val viewModel: AddAnimalViewModel by viewModels {
        requireApp().viewModelFactory
    }

    private val nameValidators = listOf<InputValidator>(
        this::emptyValidator,
    )

    private val ageValidators = listOf<InputValidator>(
        this::emptyValidator,
        this::ageValidator,
    )

    private val breedValidators = listOf<InputValidator>(
        this::emptyValidator,
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireMainActivity().supportActionBar?.apply {
            setTitle(if (isAddScreen) R.string.add_animal_screen_title else R.string.add_animal_screen_edit_title)
        }
        viewBinding.apply {
            if (!isAddScreen) {
                nameInput.text = animalToUpdate?.name.orEmpty()
                ageInput.text = animalToUpdate?.age?.toString().orEmpty()
                breedInput.text = animalToUpdate?.breed.orEmpty()
            }
            val formValidator = mapOf(
                nameInput to nameValidators,
                ageInput to ageValidators,
                breedInput to breedValidators,
            )

            addButton.apply {
                setText(
                    if (isAddScreen) R.string.add_animal_button_add else R.string.add_animal_button_update
                )
                setOnClickListener {
                    if (formValidator.validate()) {
                        if (isAddScreen) {
                            viewModel.addAnimal(
                                Animal(
                                    name = nameInput.text,
                                    age = ageInput.text.toInt(),
                                    breed = breedInput.text,
                                )
                            )
                        } else {
                            viewModel.updateAnimal(
                                animalToUpdate!!.copy(
                                    name = nameInput.text,
                                    age = ageInput.text.toInt(),
                                    breed = breedInput.text,
                                )
                            )
                        }

                    }
                }
            }

            viewModel.onResult.observe(viewLifecycleOwner) { isSuccess ->
                val messageRes =
                    if (isSuccess) R.string.add_animal_toast_saved else R.string.add_animal_toast_not_saved
                Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
                if (isSuccess) {
                    navigator.navigateUp()
                }
            }
        }
    }

    private fun emptyValidator(text: String): String? {
        return if (text.isBlank()) getString(R.string.error_required_input) else null
    }

    private fun ageValidator(text: String): String? {
        val age = text.toIntOrNull()
        return if (age == null || age !in 0..100) getString(R.string.error_age_input) else null
    }

}

private fun Animal.Companion.fromBundle(bundle: Bundle?): Animal? {
    val keys = listOf("id", "name", "age", "breed")

    return if (bundle != null && keys.all { bundle.containsKey(it) }) {
        Animal(
            id = bundle.getLong("id"),
            name = bundle.getString("name")!!,
            age = bundle.getInt("age"),
            breed = bundle.getString("breed")!!,
        )
    } else null
}

private fun Animal.toBundle(): Bundle = bundleOf(
    "id" to id,
    "name" to name,
    "age" to age,
    "breed" to breed,
)