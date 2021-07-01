package com.sample.rss.rss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.rss.common.ActionResult
import com.sample.rss.common.ActionResultDone
import com.sample.rss.common.ActionResultFailed
import com.sample.rss.common.ActionResultStarted
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RssAggregator @Inject constructor(
    private val retrofit: Retrofit,
    private val rssStorage: RssStorage
) {
    private val rssEndpoints: RssEndpoints by lazy { retrofit.create(RssEndpoints::class.java) }
    private val loadStatus = MutableLiveData<ActionResult>()

    fun getStatus(): LiveData<ActionResult> = loadStatus

    suspend fun startAsync() {
        var throwable: Throwable? = null
        loadStatus.postValue(ActionResultStarted)
        try {
            rssStorage.save(
                rssEndpoints.getFeedHome(),
                rssEndpoints.getFeedEngland(),
                rssEndpoints.getFeedWorld()
            )
        } catch (ex: Throwable) {
            throwable = ex
        } finally {
            throwable?.let {
                loadStatus.postValue(ActionResultFailed(throwable))
            } ?: loadStatus.postValue(ActionResultDone)
        }
    }
}
