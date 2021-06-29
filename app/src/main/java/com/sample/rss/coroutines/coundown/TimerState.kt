package com.sample.rss.coroutines.coundown

data class TimerState(
    val millisTotal: Long,
    val millisRemaining: Long? = null,
) {
    val progressPercentage: Float = 100f - (100f * (millisRemaining ?: 0L) / millisTotal.toFloat())
}