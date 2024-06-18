package com.jpc.chapter11.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet.Motion
import com.jpc.chapter11.utils.Utils

/**
 * @ClassName MultiTouchView
 * @Description TODO
 * @Author zzps
 * @Date 2024/6/18 14:54
 * @Version 1.0
 */
class MultiTouchView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null): View(context, attributeSet){
    private val mFirstPath = Path() // 主要动作的路径对象
    private val mSecondPath = Path() // 次要动作的路径对象
    private lateinit var mPathPaint: Paint // 路径的画笔
    private lateinit var mBeginPaint: Paint // 起点的画笔
    private lateinit var mEndPaint: Paint // 终点的画笔
    private var mFirstLastP: PointF? = null // 主要动作的上次触摸点
    private var mFirstBeginP: PointF? = null // 主要动作的本次开始触摸点
    private var mFirstEndP: PointF? = null // 主要动作的本次终点触摸点
    private var mSecondLastP: PointF? = null // 次要动作的上次触摸点
    private var mSecondBeginP: PointF? = null // 次要动作的本次开始触摸点
    private var mSecondEndP: PointF? = null // 次要动作的本次终点触摸点
    private var isFinish: Boolean = false // 是否结束触摸
    private val dip_10 = Utils.dip2px(context, 10f)
    private val dip_5 = Utils.dip2px(context, 5f)

    init {
        initView()
    }
    private fun initView() {
        mPathPaint = Paint().apply {
            strokeWidth = 5f // 画笔的线宽
            style = Paint.Style.STROKE
            color = Color.BLACK
        }
        val thinDash = DashPathEffect(floatArrayOf(dip_10, dip_10), 1f)
        mBeginPaint = Paint().apply {
            strokeWidth = 3f
            style = Paint.Style.STROKE
            color = Color.RED
            pathEffect = thinDash // 设置虚线的样式
        }
        val denseDash = DashPathEffect(floatArrayOf(dip_5, dip_5), 1f)
        mEndPaint = Paint().apply {
            strokeWidth = 3f
            style = Paint.Style.STROKE
            color = Color.GREEN
            pathEffect = denseDash // 设置虚线的样式
        }
    }
    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(mFirstPath, mPathPaint)
        canvas.drawPath(mSecondPath, mPathPaint)
        if(isFinish){
            // 分别绘制主要和次要动作的起点之间、终点之间的连线
            // 换一种写法
            mFirstBeginP?.let {
                mSecondBeginP?.let { it1 ->
                    canvas.drawLine(it.x, it.y, it1.x, it1.y, mBeginPaint)
                }
            }
            mFirstEndP?.let {
                mSecondEndP?.let { it1 ->
                    canvas.drawLine(it.x, it.y, it1.x, it1.y, mEndPaint)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val firstP = PointF(event.x, event.y)
        var secondP: PointF? = null
        if(event.pointerCount >= 2){
            secondP = PointF(event.getX(1), event.getY(1))
        }
        // 获得包括次要点在内的触摸行为
        val action = event.action and MotionEvent.ACTION_MASK
        when(action){
            MotionEvent.ACTION_DOWN -> {
                // 主要点按下
                isFinish = false
                mFirstPath.reset()
                mSecondPath.reset()
                mFirstPath.moveTo(firstP.x, firstP.y) // 移动到指定坐标点
                mFirstBeginP = PointF(firstP.x, firstP.y)
                mFirstEndP = null
            }
            MotionEvent.ACTION_MOVE -> {
                // 移动手指
                if(!isFinish){
                    // 连接上一个坐标点和当前坐标点
                    mFirstLastP?.let { mFirstPath.quadTo(it.x, it.y, firstP.x, firstP.y) }
                    secondP?.let {
                        mSecondLastP?.let { it1 ->
                            // 注意顺序
                            mSecondPath.quadTo(it1.x, it1.y, it.x, it.y)
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {}
            MotionEvent.ACTION_POINTER_DOWN -> {
                // 次要点按下
                secondP?.let {
                    mSecondPath.moveTo(it.x, it.y)
                    mSecondBeginP = PointF(it.x, it.y)
                    mSecondEndP = null
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                // 次要点松开
                isFinish = true
                mFirstEndP = PointF(firstP.x, firstP.y)
                secondP?.let {
                    mSecondEndP = PointF(it.x, it.y)
                }
                // 触发手势滑动动作
                mListener.onSlideFinish(mFirstBeginP, mFirstEndP, mSecondBeginP, mSecondEndP)
                performClick()
            }
        }
        mFirstLastP = PointF(firstP.x, firstP.y)
        secondP?.let {
            mSecondLastP = PointF(it.x, it.y)
        }
        postInvalidate() // 线程安全的方式立即刷新视图
        return true
    }
    // 声明一个手势滑动监听器
    private lateinit var mListener: SlideListener

    fun setSlideListener(listener: SlideListener){
        mListener = listener
    }
    interface SlideListener{
        fun onSlideFinish(firstBeginP: PointF?, firstEndP: PointF?, secondBeginP: PointF?, secondEndP: PointF?)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}