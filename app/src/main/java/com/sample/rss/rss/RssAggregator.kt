package com.sample.rss.rss

import com.sample.rss.rss.model.RssFeed
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RssAggregator @Inject constructor(
    private val retrofit: Retrofit,
    private val rssStorage: RssStorage
) {
    private val rssEndpoints: RssEndpoints by lazy { retrofit.create(RssEndpoints::class.java) }
    private val loadStatus = BehaviorSubject.create<Boolean>()

    fun getStatus(): Observable<Boolean> = loadStatus.distinctUntilChanged()

    fun startSync(): Observable<Unit> = Observable.combineLatest(
        rssEndpoints.getFeedHome(),
        rssEndpoints.getFeedEngland(),
        rssEndpoints.getFeedWorld(),
    ) { home: RssFeed, england: RssFeed, world: RssFeed ->
        rssStorage.saveFeed(home, england, world)
    }.doOnSubscribe { loadStatus.onNext(true) }
        .doOnComplete { loadStatus.onNext(false) }
        .subscribeOn(Schedulers.io())
}
