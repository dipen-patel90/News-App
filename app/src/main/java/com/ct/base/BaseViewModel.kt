package com.ct.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ct.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    fun launchWithViewModelScope(
        call: suspend () -> Unit,
        exceptionCallback: suspend (exception: String) -> Unit
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            viewModelScope.launch {
                exceptionCallback(
                    exception.localizedMessage
                        ?: applicationContext.getString(R.string.something_went_wrong_internet)
                )
            }
        }) {
            call.invoke()
        }
    }
}