package com.sample.rss.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.rss.common.Action
import com.sample.rss.common.ActionDone
import com.sample.rss.common.ActionFailed
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

    val onDeleted: MutableLiveData<Action> by lazy {
        MutableLiveData<Action>()
    }

    val onViewed: MutableLiveData<Action> by lazy {
        MutableLiveData<Action>()
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
                    ActionDone()
                } catch (ex: Throwable) {
                    ActionFailed(ex)
                }
            )
        }
    } ?: onViewed.postValue(ActionFailed(EmptyRssItem()))

    fun deleteNews() = rssItem.value?.let { item ->
        viewModelScope.launch {
            onDeleted.postValue(
                try {
                    repository.markAsDeleted(item)
                    ActionDone()
                } catch (ex: Throwable) {
                    ActionFailed(ex)
                }
            )
        }
    } ?: onDeleted.postValue(ActionFailed(EmptyRssItem()))
}

class EmptyRssItem : Throwable("Empty RSS Item")