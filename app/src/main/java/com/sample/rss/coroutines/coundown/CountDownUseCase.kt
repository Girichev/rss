package com.sample.rss.coroutines.coundown

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class CountDownUseCase(private val timerScope: CoroutineScope) {

    private var job: Job? = null

    private val countDown = CountDown()

    private var _timerStateFlow = MutableStateFlow(TimerState(1))
    val timerStateFlow: StateFlow<TimerState> = _timerStateFlow

    private fun stopTimer() {
        job?.cancel()
        job = null
    }

    fun startTimer(totalSec: Long, onComplete: () -> Unit) {
        stopTimer()
        job = timerScope.launch(Dispatchers.IO) {
            countDown.init(totalSec)
                .onCompletion {
                    _timerStateFlow.emit(TimerState(totalSec))
                    onComplete.invoke()
                }.collect { _timerStateFlow.emit(it) }
        }
    }
}