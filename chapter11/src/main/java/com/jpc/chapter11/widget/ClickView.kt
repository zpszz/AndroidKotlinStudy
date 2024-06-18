package com.jpc.chapter11.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jpc.chapter11.utils.Utils

class ClickView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet) {
    private val mPaint = Paint()
    private var mLastTime: Long = 0 // 上次按下手指的时间
    private var mPos: PointF? = null // 按下手指的坐标点
    private var mPressure: Float = 0f // 按压的压力值
    private var dip_10 = Utils.dip2px(context, 10f)

    init {
        mPaint.color = Color.DKGRAY
    }

    override fun onDraw(canvas: Canvas) {
        mPos?.let {
            // 以按压点为圆心在屏幕上绘制圆形
            canvas.drawCircle(it.x, it.y, dip_10*mPressure, mPaint)
        }
    }
    // 在发生触摸事件时调用
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if(it.action == MotionEvent.ACTION_DOWN || it.pressure > mPressure){
                mPos = PointF(it.x, it.y) // 获取本次触摸的坐标点
                mPressure = it.pressure // 获取本次触摸过程中的最大压力值
            }
        }
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                // 按下手指
                mLastTime = event.eventTime // 获取当前事件触发时间
            }
            MotionEvent.ACTION_MOVE -> {
                // 移动手指
            }
            MotionEvent.ACTION_UP -> {
                // 提起手指
                // 触发手势抬起事件
                mListener.onLift(event.eventTime - mLastTime, mPressure)
            }
        }
        postInvalidate() // 立即刷新视图
        return true
    }
    // 声明一个手势抬起监听器
    private lateinit var mListener: LiftListener
    fun setLiftListener(listener: LiftListener){
        mListener = listener
    }
    // 定义一个手势抬起的监听器接口
    interface LiftListener{
        fun onLift(timeInterval: Long, pressure: Float)
    }
}