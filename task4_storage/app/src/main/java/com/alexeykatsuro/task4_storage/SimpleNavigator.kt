package com.alexeykatsuro.task4_storage

import com.alexeykatsuro.task4_storage.data.entities.Animal

/**
 * Interface for simple navigation logic that implemented in [MainActivity] by fragment transactions.
 *
 * Note: for production solutions use [Jetpack Navigation](https://developer.android.com/guide/navigation).
 **/
interface SimpleNavigator {

    fun navigateToAddAnimalFragment()
    fun navigateToEditAnimalFragment(animal: Animal)
    fun navigateToSettingFragment()
    fun navigateUp()
}