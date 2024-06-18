package com.jpc.chapter11.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class InterceptLayout: LinearLayout{
    constructor(context: Context?): super(context)

    constructor(context: Context?, attributeSet: AttributeSet): super(context, attributeSet)

    // 定义一个拦截监听器
    private var mListener: InterceptListener? = null

    // 在拦截触摸事件时触发
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        mListener?.onIntercept()
        // 一般容器返回false表示不拦截，但是例如ScrollView返回true表示拦截事件，拦截下级视图的触摸动作
        return true
    }
    // 设置拦截监听器
    public fun setInterceptListener(interceptListener: InterceptListener?){
        mListener = interceptListener
    }

    // 定义一个拦截监听器接口
    interface InterceptListener{
        fun onIntercept()
    }
}