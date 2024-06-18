package com.jpc.chapter11.activity

import android.graphics.PointF
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityScaleRotateBinding
import com.jpc.chapter11.utils.PointUtil
import com.jpc.chapter11.widget.MultiTouchView
import kotlin.math.abs
import kotlin.math.sqrt

class ScaleRotateActivity : AppCompatActivity() {
    private val binding: ActivityScaleRotateBinding by lazy { ActivityScaleRotateBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.mtvGesture.setSlideListener(object : MultiTouchView.SlideListener {
            override fun onSlideFinish(
                firstBeginP: PointF?,
                firstEndP: PointF?,
                secondBeginP: PointF?,
                secondEndP: PointF?
            ) {
                firstBeginP!!
                secondBeginP!!
                firstEndP!!
                secondEndP!!
                // 上次两个触摸点之间的距离
                val preWholeDistance: Float = PointUtil.distance(firstBeginP, secondBeginP)

                // 当前两个触摸点之间的距离
                val nowWholeDistance: Float = PointUtil.distance(firstEndP, secondEndP)

                // 主要点在前后两次落点之间的距离
                val primaryDistance: Float = PointUtil.distance(firstBeginP, firstEndP)

                // 次要点在前后两次落点之间的距离
                val secondaryDistance: Float = PointUtil.distance(secondBeginP, secondEndP)
                if (abs((nowWholeDistance - preWholeDistance).toDouble()) >
                    sqrt(2.0).toFloat() / 2.0f * (primaryDistance + secondaryDistance)
                ) {
                    // 倾向于在原始线段的相同方向上移动，则判作缩放动作
                    val scaleRatio = nowWholeDistance / preWholeDistance
                    val desc = "本次手势为缩放动作，${ if(scaleRatio >= 1) "放大倍数" else "缩小比例" }为${scaleRatio}"
                    binding.tvDesc.text = desc
                } else { // 倾向于在原始线段的垂直方向上移动，则判作旋转动作
                    // 计算上次触摸事件的旋转角度
                    val preDegree: Int = PointUtil.degree(firstBeginP, secondBeginP)
                    // 计算本次触摸事件的旋转角度
                    val nowDegree: Int = PointUtil.degree(firstEndP, secondEndP)
                    val desc = "本次手势为旋转动作，${ if (nowDegree > preDegree) "顺时针" else "逆时针" }方向旋转了${ abs((nowDegree - preDegree).toDouble()) }度"
                    binding.tvDesc.text = desc
                }
            }
        })
    }
}