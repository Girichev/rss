package com.sample.rss.common

sealed class Action
class ActionDone : Action()
class ActionFailed(val throwable: Throwable): Action()