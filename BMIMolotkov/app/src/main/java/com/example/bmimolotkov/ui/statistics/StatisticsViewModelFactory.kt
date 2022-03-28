package com.example.bmimolotkov.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bmimolotkov.database.WeightDao

class StatisticsViewModelFactory(
    private val weightDao: WeightDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(weightDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
