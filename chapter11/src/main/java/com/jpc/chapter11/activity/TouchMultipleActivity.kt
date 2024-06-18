package com.jpc.chapter11.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityTouchMultipleBinding
import java.util.Locale

class TouchMultipleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTouchMultipleBinding
    private var isMinorDown: Boolean = false // 次要点是否按下
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTouchMultipleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.touch_multiple)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // 在发生触摸事件时触发
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 从开机到现在的毫秒数
        val seconds = (event.eventTime / 1000).toInt()
        var desc_major = String.format(locale = Locale.CHINA,
            "主要动作发生时间：开机距离现在%02d:%02d:%02d\n%s",
            seconds / 3600, seconds % 3600 / 60, seconds % 60, "主要动作名称是："
        )
        var desc_minor = ""
        isMinorDown = (event.pointerCount >= 2) // 触摸点大于2个
        // 获得包括次要点在内的触摸行为
        val action = event.action and MotionEvent.ACTION_MASK // and运算，是为了获取多点触控的动作
        if (action == MotionEvent.ACTION_DOWN) { // 按下手指
            desc_major = String.format("%s按下", desc_major)
        } else if (action == MotionEvent.ACTION_MOVE) { // 移动手指
            desc_major = String.format("%s移动", desc_major)
            if (isMinorDown) {
                desc_minor = String.format("%s次要动作名称是：移动", desc_minor)
            }
        } else if (action == MotionEvent.ACTION_UP) { // 提起手指
            desc_major = String.format("%s提起", desc_major)
        } else if (action == MotionEvent.ACTION_CANCEL) { // 取消手势
            desc_major = String.format("%s取消", desc_major)
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) { // 次要点按下
            desc_minor = String.format("%s次要动作名称是：按下", desc_minor)
        } else if (action == MotionEvent.ACTION_POINTER_UP) { // 次要点松开
            desc_minor = String.format("%s次要动作名称是：提起", desc_minor)
        }
        desc_major = String.format(locale = Locale.CHINA,
            "%s\n主要动作发生位置是：横坐标%f，纵坐标%f",
            desc_major, event.x, event.y
        )
        binding.tvTouchMajor.text = desc_major
        if (isMinorDown || !TextUtils.isEmpty(desc_minor)) { // 存在次要点触摸
            desc_minor = String.format(locale = Locale.CHINA,
                "%s\n次要动作发生位置是：横坐标%f，纵坐标%f",
                desc_minor, event.getX(1), event.getY(1) // 1表示次要点
            )
            binding.tvTouchMinor.text = desc_minor
        }
        return super.onTouchEvent(event)
    }
}