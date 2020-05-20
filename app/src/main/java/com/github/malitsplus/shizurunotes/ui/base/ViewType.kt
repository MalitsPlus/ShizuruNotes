package com.github.malitsplus.shizurunotes.ui.base

interface ViewType<out T> {

    /***
     * 布局ID
     */
    val layoutId: Int

    /***
     * 单个item的实例
     */
    val data: T

    /***
     * 是否包含点击事件
     */
    val isUserInteractionEnabled: Boolean
}