package com.example.bmimolotkov.ui.weights

import androidx.lifecycle.ViewModel
import com.example.bmimolotkov.database.WeightDao
import com.example.bmimolotkov.database.Weight
import com.example.bmimolotkov.database.Prefs
import kotlinx.coroutines.*
import java.time.LocalDateTime

class WeightsViewModel(
    private val weightDao: WeightDao,
    private val prefs: Prefs
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val weights = weightDao.getAllWeights()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addWeight(weight: Float) {
        uiScope.launch {
            addNewWeight(weight)
        }
    }

    fun deleteWeight(weight: Weight) {
        uiScope.launch {
            removeWeight(weight)
        }
    }

    fun editWeight(weight: Weight, newWeight: Float) {
        uiScope.launch {
            updateWeight(weight, newWeight)
        }
    }

    private suspend fun addNewWeight(weight: Float) {
        withContext(Dispatchers.IO) {
            val lastWeight = weightDao.getLastWeight()

            val id: Int
            val difference: Float
            if (lastWeight != null) {
                id = lastWeight.id + 1
                difference = weight - lastWeight.weight
            } else {
                id = 1
                difference = 0F
            }

            prefs.user?.let {
                weightDao.addWeight(
                    Weight(
                        id,
                        weight,
                        LocalDateTime.now(),
                        difference,
                        weight / (it.height * it.height)
                    )
                )
            }
        }
    }

    private suspend fun updateWeight(bmi: Weight, newWeight: Float) {
        withContext(Dispatchers.IO) {
            val newDiff = bmi.difference + newWeight - bmi.weight
            bmi.apply {
                weight = newWeight
                difference = newDiff
            }

            weightDao.updateWeight(bmi)
        }
    }

    private suspend fun removeWeight(weight: Weight) {
        withContext(Dispatchers.IO) {
            weightDao.deleteWeight(weight)
        }
    }
}
