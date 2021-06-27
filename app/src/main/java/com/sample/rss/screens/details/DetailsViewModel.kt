package com.sample.rss.screens.details

import androidx.lifecycle.MutableLiveData
import com.sample.rss.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailsRepository) :
    BaseViewModel() {
    val item = repository.item

    val onDeleted: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setLink(link: String) {
        repository.setLink(link)
    }

    fun setViewed() {
        repository.setViewed()?.subscribeOn(Schedulers.io())
            ?.subscribeBy({ it.printStackTrace() }, {})
            ?.addTo(compositeDisposable)
    }

    fun deleteNews() {
        repository.markAsDeleted()?.subscribeOn(Schedulers.io())
            ?.subscribeBy(
                { onDeleted.postValue(false) },
                { onDeleted.postValue(true) }
            )?.addTo(compositeDisposable)
    }
}