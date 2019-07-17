package com.xingray.recycleradapter

/**
 * 用于通过数据和`Item`在列表中的位置确认`ViewType`
 * 注意：在一个[RecyclerAdapter]中，映射返回的`ViewType`必须是唯一的，
 * 一个[ViewHolder]对应一个`ViewType`，否则会导致程序运行出错
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2018/8/2 14:37
 */
interface ViewTypeMapper<T> {
    fun getViewType(t: T, position: Int): Int
}