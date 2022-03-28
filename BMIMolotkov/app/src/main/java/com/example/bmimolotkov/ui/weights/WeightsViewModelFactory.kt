package com.example.bmimolotkov.ui.weights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bmimolotkov.database.WeightDao
import com.example.bmimolotkov.database.Prefs

class WeightsViewModelFactory(
    private val weightDao: WeightDao,
    private val prefs: Prefs
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightsViewModel::class.java)) {
            return WeightsViewModel(weightDao, prefs) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
