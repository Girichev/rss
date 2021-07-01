package com.sample.rss.coroutines

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhetCreated(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenCreated {
        this@launchWhetCreated.collect()
    }
}

fun <T> Flow<T>.launchWhetStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenStarted {
        this@launchWhetStarted.collect()
    }
}

fun <T> Flow<T>.launchWhetResumed(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenResumed {
        this@launchWhetResumed.collect()
    }
}