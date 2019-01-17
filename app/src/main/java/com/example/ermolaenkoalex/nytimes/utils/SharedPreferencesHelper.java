package com.example.ermolaenkoalex.nytimes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;

import androidx.preference.PreferenceManager;

public class SharedPreferencesHelper {
    private static final String KEY_LOGO = "KEY_LOGO";
    private static final String KEY_SECTION = "KEY_SECTION";

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesHelper(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public boolean getShowLogo() {
        return sharedPreferences.getBoolean(KEY_LOGO, true);
    }

    public void setShowLogo(boolean showLogo) {
        sharedPreferences.edit().putBoolean(KEY_LOGO, showLogo).apply();
    }

    public Section getSection() {
        return Section.values()[sharedPreferences.getInt(KEY_SECTION, 0)];
    }

    public void setSection(Section section) {
        sharedPreferences.edit().putInt(KEY_SECTION, section.ordinal()).apply();
    }

    public boolean isEditEnabled() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_key_edit_news), false);
    }

    public boolean isDeleteEnabled() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_key_delete_news), false);
    }
}
