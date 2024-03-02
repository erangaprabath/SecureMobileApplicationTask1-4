package com.example.task02;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class language {
    private static final String PREF_KEY_SELECTED_LANGUAGE = "selectedLanguage";
    private static final String DEFAULT_LANGUAGE_CODE = "en";

    private final SharedPreferences sharedPreferences;
    private final Context context;

    public language(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
    }

    public String getSelectedLanguage() {
        return sharedPreferences.getString(PREF_KEY_SELECTED_LANGUAGE, DEFAULT_LANGUAGE_CODE);
    }

    public void setSelectedLanguage(String languageCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY_SELECTED_LANGUAGE, languageCode);
        editor.apply();

    }

    public void setLocale() {
        String languageCode = getSelectedLanguage();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration(context.getResources().getConfiguration());
            configuration.locale = locale;


        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

    }
}
