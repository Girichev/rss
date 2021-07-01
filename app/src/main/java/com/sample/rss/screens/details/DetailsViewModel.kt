package com.sample.rss.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.rss.common.ActionResult
import com.sample.rss.common.ActionResultDone
import com.sample.rss.common.ActionResultFailed
import com.sample.rss.common.base.BaseViewModel
import com.sample.rss.coroutines.coundown.CountDownUseCase
import com.sample.rss.room.view.RssItemView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailsRepository) :
    BaseViewModel() {

    val countdown = CountDownUseCase(viewModelScope)

    val rssItem: LiveData<RssItemView> by lazy { repository.rssItem }

    val onDeleted: MutableLiveData<ActionResult> by lazy {
        MutableLiveData<ActionResult>()
    }

    val onViewed: MutableLiveData<ActionResult> by lazy {
        MutableLiveData<ActionResult>()
    }

    fun setGuid(guid: String) {
        repository.setGuid(guid)
    }

    fun startViewedCountDown() {
        countdown.startTimer(3) { setViewed() }
    }

    private fun setViewed() = rssItem.value?.let { item ->
        viewModelScope.launch {
            onViewed.postValue(
                try {
                    repository.markAsViewed(item)
                    ActionResultDone
                } catch (ex: Throwable) {
                    ActionResultFailed(ex)
                }
            )
        }
    } ?: onViewed.postValue(ActionResultFailed(EmptyRssItem()))

    fun deleteNews() = rssItem.value?.let { item ->
        viewModelScope.launch {
            onDeleted.postValue(
                try {
                    repository.markAsDeleted(item)
                    ActionResultDone
                } catch (ex: Throwable) {
                    ActionResultFailed(ex)
                }
            )
        }
    } ?: onDeleted.postValue(ActionResultFailed(EmptyRssItem()))
}

class EmptyRssItem : Throwable("Empty RSS Item")