package com.example.bmimolotkov.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bmimolotkov.database.WeightDao
import com.example.bmimolotkov.database.Weight
import com.example.bmimolotkov.database.Prefs
import kotlinx.coroutines.*

class ProfileViewModel(
    private val weightDao: WeightDao,
    private val prefs: Prefs
) : ViewModel() {

    val lastWeight = MutableLiveData<Weight>()
    val user = prefs.user

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    init {
        uiScope.launch {
            lastWeight.value = getLastWeight()
        }
    }

    private suspend fun getLastWeight(): Weight? {
        return withContext(Dispatchers.IO) {
            weightDao.getLastWeight()
        }
    }
}
