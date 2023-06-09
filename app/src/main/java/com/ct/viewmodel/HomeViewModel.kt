package com.ct.viewmodel

import com.ct.api.APIResponse
import com.ct.base.BaseViewModel
import com.ct.extention.getValueBlockedOrNull
import com.ct.model.dto.response.toUINewsHeadline
import com.ct.model.vo.UINewsHeadline
import com.ct.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
* Created ViewModel which will be shared between NewsHeadlinesFragment & NewsDescriptionFragment
* */
@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _newsHeadlines = MutableSharedFlow<APIResponse<List<UINewsHeadline>>>(1)
    val newsHeadlines = _newsHeadlines.asSharedFlow()

    /**
    * Get headline from the API
    * -Filter news where title & date is not-null
    * -Convert the server response object to view-object
    * -Sort the response by date
    * */
    fun getTopHeadlines() = launchWithViewModelScope(
        call = {
            _newsHeadlines.emit(APIResponse.Loading())

            val result = newsRepository.getTopHeadlines()
            val articles = result.body()?.articles
            if (result.isSuccessful && articles != null) {
                val filtered = articles
                    .filter { it.title != null && it.publishedAt != null }
                    .map { it.toUINewsHeadline() }
                    .sortedByDescending { it.publishedAt }
                filtered.firstOrNull()?.let {
                    it.isSelected = true
                    updateListSelection(it)
                }
                _newsHeadlines.emit(APIResponse.Success(filtered))
            } else {
                _newsHeadlines.emit(APIResponse.Failure(result.errorBody().toString()))
            }
        },
        exceptionCallback = {
            _newsHeadlines.emit(APIResponse.Failure(it))
        })

    private val _selectedHeadline = MutableSharedFlow<UINewsHeadline>(1)
    val selectedHeadline = _selectedHeadline.asSharedFlow()
    /**
    * When user select any news item mark it selected in list object
    * and also update the selectedHeadline object to display the news on description screen
    * */
    fun updateListSelection(selectedHeadline: UINewsHeadline) = launchWithViewModelScope(
        call = {
            _selectedHeadline.emit(selectedHeadline)
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

    init {
        getTopHeadlines()
    }
}