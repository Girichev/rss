package com.sample.rss.room

fun String.toRoomSearch() = if (this.isEmpty()) "%" else "%$this%"

