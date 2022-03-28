package com.example.bmimolotkov.ui.statistics

import androidx.lifecycle.ViewModel
import com.example.bmimolotkov.database.WeightDao
import kotlinx.coroutines.*
import java.time.LocalDateTime

class StatisticsViewModel(
    private val weightDao: WeightDao
) : ViewModel() {

    private var viewModelJob = Job()

    val lastWeekWeights = weightDao.getWeightsByDate(LocalDateTime.now().minusWeeks(1))
    val lastMonthWeights = weightDao.getWeightsByDate(LocalDateTime.now().minusWeeks(1))
    val lastQuarterWeights = weightDao.getWeightsByDate(LocalDateTime.now().minusMonths(3))
    val lastYearWeights = weightDao.getWeightsByDate(LocalDateTime.now().minusYears(1))

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
