package com.sample.rss.coroutines.coundown

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class CountDown {

    fun init(totalSec: Long) = (totalSec downTo 0).asFlow()
        .onEach { delay(1_000L) }
        .onStart { emit(totalSec) }
        .conflate()
        .transform { remainingMillis ->  emit(TimerState(totalSec, remainingMillis)) }
}