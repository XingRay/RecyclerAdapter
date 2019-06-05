package com.xingray.recycleradapter

import java.util.concurrent.atomic.AtomicInteger

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/6/5 21:06
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
object UniqueId {
    private val id: AtomicInteger = AtomicInteger()
    fun get(): Int {
        return id.getAndIncrement()
    }
}