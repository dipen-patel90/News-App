package com.ct.viewmodel

import androidx.lifecycle.viewModelScope
import com.ct.api.APIResponse
import com.ct.base.BaseViewModel
import com.ct.extention.getValueBlockedOrNull
import com.ct.model.dto.response.toUINewsHeadline
import com.ct.model.vo.UINewsHeadline
import com.ct.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _newsHeadlines = MutableSharedFlow<APIResponse<List<UINewsHeadline>>>(1)
    val newsHeadlines = _newsHeadlines.asSharedFlow()
    fun getTopHeadlines() = launchWithViewModelScope(
        call = {
            _newsHeadlines.emit(APIResponse.Loading())

            val result = newsRepository.getTopHeadlines()
            val articles = result.body()?.articles
            if (result.isSuccessful && articles != null) {
                val filtered = articles.filter { it.title != null && it.publishedAt != null }
                    .map { it.toUINewsHeadline() }
                    .sortedByDescending { it.publishedAt }
                _newsHeadlines.emit(APIResponse.Success(filtered))
            } else {
                _newsHeadlines.emit(APIResponse.Failure(result.errorBody().toString()))
            }
        },
        exceptionCallback = {
            _newsHeadlines.emit(APIResponse.Failure(it))
        })

    private val _selectedHeadline = MutableSharedFlow<UINewsHeadline>(1)
    val selectedHeadlines = _selectedHeadline.asSharedFlow()
    fun selectedHeadline(uiNewsHeadline: UINewsHeadline) = viewModelScope.launch {
        _selectedHeadline.emit(uiNewsHeadline)
    }

    fun updateListSelection(selectedHeadline: UINewsHeadline) = launchWithViewModelScope(
        call = {
            _newsHeadlines.getValueBlockedOrNull()?.let {
                it.data?.map {
                    it.isSelected = selectedHeadline.title == it.title
                    it
                }?.let {
                    _newsHeadlines.emit(APIResponse.Success(it))
                }
            }
        },
        exceptionCallback = {}
    )

    fun clearListSelection() = launchWithViewModelScope(
        call = {
            _newsHeadlines.getValueBlockedOrNull()?.let {
                it.data?.map {
                    it.isSelected = false
                    it
                }?.let {
                    _newsHeadlines.emit(APIResponse.Success(it))
                }
            }
        },
        exceptionCallback = {}
    )

    init {
        getTopHeadlines()
    }
}