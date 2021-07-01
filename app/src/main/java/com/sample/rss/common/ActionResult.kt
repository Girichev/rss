package com.sample.rss.common

sealed class ActionResult
object ActionResultStarted : ActionResult()
object ActionResultDone : ActionResult()
class ActionResultFailed(val throwable: Throwable): ActionResult()