package com.sample.rss.common.base

interface BaseMapper<SRC, DST> {
    fun map(src: SRC): DST
}