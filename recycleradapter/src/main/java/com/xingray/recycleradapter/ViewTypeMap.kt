package com.xingray.recycleradapter

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/7/16 21:24
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ViewTypeMap<T : Any> : HashMap<Class<T>, (T, Int) -> Int>() {
    override fun put(key: Class<T>, value: (T, Int) -> Int): ((T, Int) -> Int)? {
        return super.put(key, value)
    }

    override fun get(key: Class<T>): ((T, Int) -> Int)? {
        return super.get(key)
    }
}