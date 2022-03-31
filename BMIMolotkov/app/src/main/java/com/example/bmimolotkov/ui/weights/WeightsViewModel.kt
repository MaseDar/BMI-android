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

    fun addTestDatas(){
        uiScope.launch {
            addTestData()
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

     private suspend fun addTestData() {
        withContext(Dispatchers.IO) {
            val lastWeight = weightDao.getLastWeight()
            val weight = 70f
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
                        id+4,
                        weight + 10,
                        LocalDateTime.now().minusDays(2),
                        difference,
                        weight + 10/ (it.height * it.height)
                    ),
                )
                weightDao.addWeight(
                    Weight(
                        id + 3,
                        weight - 10,
                        LocalDateTime.now().minusMonths(1),
                        -20f,
                        weight - 10/ (it.height * it.height)
                    ),
                )
                weightDao.addWeight(
                    Weight(
                        id + 2,
                        weight + 24,
                        LocalDateTime.now().minusMonths(4),
                        34f,
                        weight + 24/ (it.height * it.height)
                    ),
                )
                weightDao.addWeight(
                    Weight(
                        id + 1,
                        weight - 30,
                        LocalDateTime.now().minusMonths(10),
                        -54f,
                        weight - 30 / (it.height * it.height)
                    ),
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
