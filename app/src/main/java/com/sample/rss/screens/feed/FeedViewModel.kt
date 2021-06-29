package com.sample.rss.screens.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sample.rss.common.base.BaseViewModel
import com.sample.rss.room.view.RssItemView
import com.sample.rss.rss.RssAggregator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    rssAggregator: RssAggregator
) : BaseViewModel() {

    private val searchQuery: MutableLiveData<String> by lazy { MutableLiveData<String>("") }

    val loadingStatus: LiveData<Boolean> by lazy { rssAggregator.getStatus() }

    val feed: LiveData<List<RssItemView>> by lazy {
        Transformations.switchMap(searchQuery) { feedRepository.getFeed(it) }
    }

    fun search(query: String?) {
        searchQuery.postValue(query ?: "")
    }
}