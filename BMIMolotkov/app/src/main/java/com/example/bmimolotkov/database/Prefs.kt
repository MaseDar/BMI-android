package com.example.bmimolotkov.database

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

class Prefs(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    var user: User?
        get() = sharedPreferences.getString(KEY_USER, null)?.let { gson.fromJson(it, User::class.java) }
        set(value) {
            sharedPreferences.edit { putString(KEY_USER, gson.toJson(value)) }
        }

    companion object {
        const val PREFS_NAME = "app_preferences"

        const val KEY_USER = "user"
    }
}