package com.example.ermolaenkoalex.nytimes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;

import static android.content.Context.MODE_PRIVATE;

public final class SharedPreferencesHelper {
    private static final String APP_PREFS = "APP_PREFS";
    private static final String KEY_LOGO = "KEY_LOGO";
    private static final String KEY_SECTION = "KEY_SECTION";

    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
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
}
