package com.example.bmimolotkov.ui.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmimolotkov.database.User
import com.example.bmimolotkov.database.Prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HelpViewModel(
    private val prefs: Prefs
) : ViewModel() {

    private val _user = MutableLiveData<User?>()

    val user: LiveData<User?>
        get() = _user

    init {
        viewModelScope.launch {
            delay(2000)
            _user.value = prefs.user
        }
    }
}
