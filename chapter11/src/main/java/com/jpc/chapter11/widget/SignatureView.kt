package com.jpc.chapter11.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jpc.chapter11.R
import com.jpc.chapter11.bean.PathPosition

class SignatureView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    private val mPathPaint = Paint() // 声明一个画笔对象
    private val mPath = Path() // 声明一个路径对象
    private var mPathPaintColor = Color.BLACK // 画笔颜色
    private var mStrokeWidth = 3.0f // 画笔线宽
    private var mPathPos = PathPosition() // 路径位置
    private val mPathList = ArrayList<PathPosition>() // 路径位置列表
    private var mLastPos: PointF? = null // 上次触摸点的横轴坐标

    init {
        if(attributeSet != null){
            // 回收属性数组描述
            val typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.SignatureView)
            mPathPaintColor = typedArray.getColor(R.styleable.SignatureView_paint_color, Color.BLACK)
            mStrokeWidth = typedArray.getFloat(R.styleable.SignatureView_stroke_width, 3.0f)
            typedArray.recycle() // 回收属性数组描述
        }
        initView()
    }

    // 初始化视图
    private fun initView(){
        // 设置画笔的样式
        mPathPaint.strokeWidth = mStrokeWidth
        mPathPaint.color = mPathPaintColor
        mPathPaint.style = Paint.Style.STROKE // 画笔类型 STROKE表示空心， FILL实心
        //isDrawingCacheEnabled = true
    }

    // 清空画布
    fun clear(){
        mPath.reset() // 重置路径对象
        mPathList.clear() // 清空路径列表
        postInvalidate() // 立即刷新视图，是线程安全的方法
    }
    // 撤销上一次绘图（取消上一步）
    fun revoke(){
        if (mPathList.isNotEmpty()){
            // 移除路径列表的最后一个路径
            mPathList.removeAt(mPathList.size - 1)
            mPath.reset() // 重置路径对象
            for (index in mPathList.indices){
                val pathPosition = mPathList[index]
                // 移动到上一个坐标点
                pathPosition.prePos?.let { mPath.moveTo(it.x, it.y) }
                // 连接上一个坐标点和下一个坐标点
                pathPosition.prePos?.let { prePos ->
                    pathPosition.nextPos?.let { nextPos ->
                        mPath.quadTo(prePos.x, prePos.y, nextPos.x, nextPos.y)
                    }
                }
            }
            postInvalidate() // 立即刷新视图
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(mPath, mPathPaint) // 在画布上绘制指定的路径线条
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            // 按下手指
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(event.x, event.y)
                mPathPos.prePos = PointF(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                mLastPos?.let { mPath.quadTo(it.x, it.y, event.x, event.y) }
                mPathPos.nextPos = PointF(event.x, event.y)
                mPathList.add(mPathPos)
                mPathPos = PathPosition()
                mPathPos.prePos = PointF(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                mLastPos?.let { mPath.quadTo(it.x, it.y, event.x, event.y) }
            }
        }
        mLastPos = PointF(event.x, event.y)
        postInvalidate()
        return true
    }

    fun captureView(): Bitmap{
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}