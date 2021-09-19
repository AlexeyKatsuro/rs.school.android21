package com.alexeykatsuro.task4_storage.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.databinding.AnimalItemBinding

typealias OnAnimalClick = (Animal) -> Unit

class AnimalsAdapter(private val onClick: OnAnimalClick, private val onLongClick: OnAnimalClick) :
    ListAdapter<Animal, AnimalsAdapter.AnimalViewHolder>(AnimalDiffCallback) {

    class AnimalViewHolder(
        private val itemBinding: AnimalItemBinding,
        onClick: OnAnimalClick,
        onLongClick: OnAnimalClick
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var currentAnimal: Animal? = null

        init {
            itemBinding.card.apply {

                setOnClickListener {
                    currentAnimal?.let {
                        onClick(it)
                    }
                }
                setOnLongClickListener {
                    currentAnimal?.let {
                        onLongClick(it)
                        true
                    } ?: false
                }
            }
        }

        fun bind(animal: Animal) {
            currentAnimal = animal
            itemBinding.apply {
                nameText.text = animal.name
                ageText.text = animal.age.toString()
                breedText.text = animal.breed
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val itemBinding =
            AnimalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(itemBinding, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = getItem(position)
        holder.bind(animal)
    }
}

object AnimalDiffCallback : DiffUtil.ItemCallback<Animal>() {
    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem == newItem
    }
}
