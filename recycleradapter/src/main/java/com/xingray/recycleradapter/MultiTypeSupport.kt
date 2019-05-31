package com.xingray.recycleradapter

/**
 * email : leixing1012@qq.com
 *
 * @author : leixing
 * @date : 2018/8/2 14:26
 *
 *
 * description : [RecyclerAdapter]的多布局支持接口，接口的定义同
 * [androidx.recyclerview.widget.RecyclerView.ViewHolder]类似。
 */
interface MultiTypeSupport<T> {
    /**
     * 根据数据和位置返回`ViewType`
     *
     * @param items    展示的数据
     * @param position 位置
     * @return `ViewType` 用具指定布局的类型
     */
    fun getItemViewType(items: List<T>, position: Int): Int

    /**
     * 根据`ViewType`获取布局的资源id
     *
     * @param viewType 布局类型
     * @return 布局资源ID
     */
    fun getLayoutId(viewType: Int): Int
}
