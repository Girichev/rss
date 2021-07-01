package com.sample.rss.common

sealed class ActionResult
object ActionResultStarted : ActionResult()
object ActionResultDone : ActionResult()
data class ActionResultFailed(val throwable: Throwable): ActionResult()