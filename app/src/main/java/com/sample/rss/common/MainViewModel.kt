package com.sample.rss.common

import com.sample.rss.common.base.BaseViewModel
import com.sample.rss.rss.RssAggregator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val rssAggregator: RssAggregator): BaseViewModel() {

    fun syncFeed(){
        rssAggregator.startSync()
            .subscribeBy(
                { it.printStackTrace() },
                { /* All OK */ }
            ).addTo(compositeDisposable)
    }
}