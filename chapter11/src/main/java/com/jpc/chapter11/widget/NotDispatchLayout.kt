package com.jpc.chapter11.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class NotDispatchLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private var mListener: NotDispatchListener? = null // 声明一个分发监听器对象
    // 在分发触摸事件时触发
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        mListener?.onNotDispatch() // 触发分发监听器的回调方法
        // 一般容器默认返回true，即允许分发给下级
        return false // 这里返回false，表示不允许分发给下级
    }

    // 设置分发监听器
    fun setNotDispatchListener(listener: NotDispatchListener?) {
        mListener = listener
    }

    // 定义一个分发监听器接口
    interface NotDispatchListener {
        fun onNotDispatch()
    }
}