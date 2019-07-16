package com.xingray.recycleradapter

/**
 * 初始化
 *
 * @author : leixing
 * @date : 19-7-14
 * email : leixing1012@qq.com
 *
 */
interface Initializer<T> {
    fun initialize(t: T)
}