package com.jpc.chapter11.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.jpc.chapter11.utils.Utils

class SingleTouchView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet){
    private lateinit var mPathPaint : Paint // 声明一个路径的画笔对象
    private lateinit var mBeginPaint : Paint // 声明一个起点的画笔对象
    private lateinit var mEndPaint : Paint // 声明一个终点的画笔对象
    private var mLastPos: PointF? = null // 路径中的上次触摸点
    private var mBeginPos: PointF? = null // 本次触摸过程中的起点
    private var mEndPos: PointF? = null // 本次触摸过程中的终点
    private val mPath = Path() // 路径对象
    private val dip_17 = Utils.dip2px(context, 17f)
    private lateinit var mListener: FlipListener

    init {
        initView()
    }

    private fun initView(){
        mPathPaint = Paint().apply {
            strokeWidth = 5f; // 设置画笔的线宽
            style = Paint.Style.STROKE // 画笔的类型 STROKE空心  FILL实心
            color = Color.BLACK // 画笔颜色
        }
        mBeginPaint = Paint().apply {
            color = Color.RED // 起点画笔的颜色
            textSize = dip_17.toFloat() // 起点画笔的文字大小
        }
        mEndPaint = Paint().apply {
            color = Color.BLUE // 终点画笔的颜色
            textSize = dip_17.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(mPath, mPathPaint) // 根据路径绘制
        // 如果存在起点、终点则绘制出来
        mBeginPos?.also {
            canvas.drawCircle(it.x, it.y, 10f, mBeginPaint)
            canvas.drawText("起点", it.x-dip_17, it.y+dip_17, mBeginPaint)
        }
        mEndPos?.also {
            canvas.drawCircle(it.x, it.y, 10f, mEndPaint)
            canvas.drawText("终点", it.x-dip_17, it.y+dip_17, mEndPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mPath.reset()
                mPath.moveTo(event.x, event.y)
                mBeginPos = PointF(event.x, event.y)
                mEndPos = null
            }
            MotionEvent.ACTION_MOVE -> {
                // 连接上一个坐标点与当前坐标点
                mLastPos?.let { mPath.quadTo(it.x, it.y, event.x, event.y) }
            }
            MotionEvent.ACTION_UP -> {
                mEndPos = PointF(event.x, event.y)
                mLastPos?.let { mPath.quadTo(it.x, it.y, event.x, event.y) }
                mListener.onFlipFinish(mBeginPos!!, mEndPos!!)
            }
        }
        event?.let { mLastPos = PointF(it.x, it.y) }
        postInvalidate()
        return true
    }
    fun setFlipListener(listener: FlipListener){
        mListener = listener
    }
    interface FlipListener{
        fun onFlipFinish(beginPos: PointF, endPos: PointF)
    }
}