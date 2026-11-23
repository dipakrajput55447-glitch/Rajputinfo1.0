package com.rajputinfo.app;

import android.content.Context;
import android.content.SharedPreferences;

public class CreditsManager {
    private static final String PREF = "rajput_prefs";
    private static final String K_CR = "credits";
    private static final String K_REG = "registered";
    private final SharedPreferences prefs;

    public CreditsManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public boolean isRegistered() { return prefs.getBoolean(K_REG, false); }

    public void register() {
        prefs.edit().putBoolean(K_REG, true).putInt(K_CR, 50).apply();
    }

    public int getCredits() { return prefs.getInt(K_CR, 0); }

    public boolean spend() {
        int c = getCredits();
        if (c <= 0) return false;
        prefs.edit().putInt(K_CR, c - 1).apply();
        return true;
    }

    public void add(int n) {
        prefs.edit().putInt(K_CR, getCredits() + n).apply();
    }
}
