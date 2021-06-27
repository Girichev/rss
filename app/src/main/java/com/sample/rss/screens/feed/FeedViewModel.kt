package com.sample.rss.screens.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sample.rss.common.base.BaseViewModel
import com.sample.rss.room.view.RssItemView
import com.sample.rss.rss.RssAggregator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedRepository: FeedRepository, rssAggregator: RssAggregator) : BaseViewModel() {

    val loadingStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val searchQuery: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    val items: LiveData<List<RssItemView>> by lazy {
        Transformations.switchMap(searchQuery) { feedRepository.getFeed(it) }
    }

    init {
        rssAggregator.getStatus()
            .subscribe { loadingStatus.postValue(it) }
            .addTo(compositeDisposable)
    }

    fun setSearchMask(query: String?) {
        searchQuery.postValue(query ?: "")
    }
}