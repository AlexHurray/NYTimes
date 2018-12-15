package com.example.ermolaenkoalex.nytimes.ui.preferences;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.ermolaenkoalex.nytimes.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class PreferencesActivity extends AppCompatActivity {
    public static void start(Activity activity) {
        Intent startIntent = new Intent(activity, PreferencesActivity.class);
        activity.startActivity(startIntent);
    }

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();
    }

    public static class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String LOG_TAG = "PreferenceFragment";

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.tr_pref, rootKey);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            configureSummaryEntries();
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            setSummaryFor(findPreference(key));
        }

        private void configureSummaryEntries() {
            setSummaryFor(findPreference(getString(R.string.pref_key_delete_news)));
            setSummaryFor(findPreference(getString(R.string.pref_key_edit_news)));
        }

        private void setSummaryFor(Preference preference) {
            if (preference instanceof CheckBoxPreference) {
                preference.setSummary(preference.getSummary());
            } else {
                Log.d(LOG_TAG, "check preferences type");
            }
        }
    }
}
