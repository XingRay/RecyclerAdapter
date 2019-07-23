package com.xingray.recycleradapter

/**
 * 用于标注[ViewHolder]所绑定的布局的{@code LayoutId}
 *
 * @author : leixing
 * @date : 2019/6/5 20:54
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutId(val value: Int)

val <VH> Class<VH>.layoutId: Int
    get() {
        val annotation = getAnnotation(LayoutId::class.java)
                ?: throw IllegalArgumentException("View Holder Class must have @LayoutId Annotation")
        return annotation.value
    }