package com.sample.rss.coroutines.coundown

data class TimerState(val secondsTotal: Long, val secondsRemaining: Long? = null) {
    val progressPercentage: Float = 100f - (100f * (secondsRemaining ?: 0L) / secondsTotal.toFloat())
}