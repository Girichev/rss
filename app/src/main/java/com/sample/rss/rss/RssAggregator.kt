package com.sample.rss.rss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RssAggregator @Inject constructor(
    private val retrofit: Retrofit,
    private val rssStorage: RssStorage
) {
    private val rssEndpoints: RssEndpoints by lazy { retrofit.create(RssEndpoints::class.java) }
    private val loadStatus = MutableLiveData(false)

    fun getStatus(): LiveData<Boolean> = loadStatus

    suspend fun startAsync() {
        loadStatus.postValue(true)
        rssStorage.save(
            rssEndpoints.getFeedHome(),
            rssEndpoints.getFeedEngland(),
            rssEndpoints.getFeedWorld()
        )
        loadStatus.postValue(false)
    }
}
