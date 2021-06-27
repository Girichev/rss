package com.sample.rss.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val compositeDisposable = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}