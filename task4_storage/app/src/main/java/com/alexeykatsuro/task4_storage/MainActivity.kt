package com.alexeykatsuro.task4_storage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.alexeykatsuro.task4_storage.data.entities.Animal
import com.alexeykatsuro.task4_storage.ui.add.AddAnimalFragment
import com.alexeykatsuro.task4_storage.ui.main.MainFragment
import com.alexeykatsuro.task4_storage.ui.setting.SettingFragment

/**
 * This application implemented with Single Activity approach, this is recommended way
 * for building modern applications.
 *
 * In short, Single Activity approach is when you use only one activity as host for fragments.
 * And you represents your Screen as separated fragments, and for navigation between screen you
 * manipulate with stack of fragments by [FragmentManager].
 *
 * Why you should use Single Activity?
 * See this [article](https://habr.com/ru/company/redmadrobot/blog/426617/).
 */
class MainActivity : AppCompatActivity(), SimpleNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<MainFragment>(R.id.container)
            }
        }
    }

    override fun navigateToAddAnimalFragment() {
        supportFragmentManager.commit {
            replace<AddAnimalFragment>(R.id.container)
            addToBackStack(null)
        }
    }

    override fun navigateToEditAnimalFragment(animal: Animal) {
        supportFragmentManager.commit {
            replace(R.id.container, AddAnimalFragment.createEditAnimalFragment(animal))
            addToBackStack(null)
        }
    }

    override fun navigateToSettingFragment() {
        supportFragmentManager.commit {
            replace<SettingFragment>(R.id.container)
            addToBackStack(null)
        }
    }

    override fun navigateUp() {
        supportFragmentManager.popBackStack()
    }
}