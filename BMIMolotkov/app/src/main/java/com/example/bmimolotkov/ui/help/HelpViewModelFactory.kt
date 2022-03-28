package com.example.bmimolotkov.ui.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bmimolotkov.database.Prefs

class HelpViewModelFactory(
    private val prefs: Prefs
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelpViewModel::class.java)) {
            return HelpViewModel(prefs) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
