package id.ac.ui.cs.mobileprogramming.razrinn.focus.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import id.ac.ui.cs.mobileprogramming.razrinn.focus.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}