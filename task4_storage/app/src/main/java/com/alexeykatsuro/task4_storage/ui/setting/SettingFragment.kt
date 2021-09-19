package com.alexeykatsuro.task4_storage.ui.setting

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference.SummaryProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.alexeykatsuro.task4_storage.R
import com.alexeykatsuro.task4_storage.data.AnimalListOrderBy
import com.alexeykatsuro.task4_storage.domain.datasources.DataSourceType
import com.alexeykatsuro.task4_storage.domain.preferences.PreferencesStorage
import com.alexeykatsuro.task4_storage.utils.localize
import com.alexeykatsuro.task4_storage.utils.requireMainActivity

/**
 * This preferences screen creates a hierarchy in the code, for flexibility
 * and the ability to change the enable/disable state of some settings
 */
class SettingFragment : PreferenceFragmentCompat() {

    /**
     * Class that provide summary(subtitle description) for [orderListPreference].
     *
     * If no value selected show text hint in summary, otherwise show selected value
     */
    class OrderBySummaryProvider private constructor() : SummaryProvider<ListPreference> {
        companion object {
            private var sOrderBySummaryProvider: OrderBySummaryProvider? = null

            val instance: OrderBySummaryProvider?
                get() {
                    if (sOrderBySummaryProvider == null) {
                        sOrderBySummaryProvider = OrderBySummaryProvider()
                    }
                    return sOrderBySummaryProvider
                }
        }

        override fun provideSummary(preference: ListPreference): CharSequence {
            return if (TextUtils.isEmpty(preference.entry)) {
                preference.context.getString(R.string.setting_list_order_summary)
            } else {
                preference.entry
            }
        }
    }

    private lateinit var orderListPreference: ListPreference
    private lateinit var dbmsListPreference: ListPreference
    private lateinit var enableOrderPreference: SwitchPreferenceCompat


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireMainActivity().supportActionBar?.apply {
            setTitle(R.string.setting_screen_title)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)



        orderListPreference = ListPreference(context).apply {
            key = PreferencesStorage.Keys.ANIMAL_LIST_ORDER_BY
            setTitle(R.string.setting_list_order_title)
            summaryProvider = OrderBySummaryProvider.instance
            entries = AnimalListOrderBy.values().map { it.localize(context) }.toTypedArray()
            entryValues = AnimalListOrderBy.values().map { it.key }.toTypedArray()
            setIcon(R.drawable.ic_sort)
        }

        dbmsListPreference = ListPreference(context).apply {
            key = PreferencesStorage.Keys.DATA_SOURCE_TYPE
            setTitle(R.string.setting_list_dbms_title)
            setDefaultValue(DataSourceType.default.key)

            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
            entries = DataSourceType.values().map { it.localize(context) }.toTypedArray()
            entryValues = DataSourceType.values().map { it.key }.toTypedArray()
            setIcon(R.drawable.ic_storage)
            setOnPreferenceChangeListener { _, _ ->
                Toast.makeText(
                    requireContext(),
                    R.string.setting_list_dbms_restart_hint,
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
        }
        enableOrderPreference = SwitchPreferenceCompat(context).apply {
            key = PreferencesStorage.Keys.IS_ORDER_ENABLED
            setDefaultValue(false)
            setTitle(R.string.setting_order_enable_title)
            this.setOnPreferenceChangeListener { _, newValue ->
                orderListPreference.isEnabled = newValue as Boolean
                true
            }
        }


        screen.addPreference(dbmsListPreference)
        screen.addPreference(enableOrderPreference)
        screen.addPreference(orderListPreference)

        preferenceScreen = screen
    }

    override fun onBindPreferences() {
        orderListPreference.isEnabled = enableOrderPreference.isChecked
    }
}