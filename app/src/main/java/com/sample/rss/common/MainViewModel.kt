package com.sample.rss.common

import androidx.lifecycle.viewModelScope
import com.sample.rss.common.base.BaseViewModel
import com.sample.rss.rss.RssAggregator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val rssAggregator: RssAggregator): BaseViewModel() {

    fun syncFeed(){
        viewModelScope.launch { rssAggregator.startAsync() }
    }
}