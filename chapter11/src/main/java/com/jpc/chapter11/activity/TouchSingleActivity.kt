package com.jpc.chapter11.activity

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityTouchSingleBinding
import java.util.Locale

class TouchSingleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTouchSingleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTouchSingleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.touch_single)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    // 在发生触摸事件时触发
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 从开机到现在的毫秒数
        val seconds = (event!!.eventTime / 1000).toInt()
        var desc = String.format(locale = Locale.CHINA,
            "动作发生时间：开机距离现在%02d:%02d:%02d",
            seconds / 3600, seconds % 3600 / 60, seconds % 60
        )
        desc = String.format("%s\n动作名称是：", desc)

        val action = event.action // 获得触摸事件的动作类型
        when (action) {
            MotionEvent.ACTION_DOWN -> { // 按下手指
                desc = String.format("%s按下", desc)
            }
            MotionEvent.ACTION_MOVE -> { // 移动手指
                desc = String.format("%s移动", desc)
            }
            MotionEvent.ACTION_UP -> { // 提起手指
                desc = String.format("%s提起", desc)
            }
            MotionEvent.ACTION_CANCEL -> { // 取消手势
                desc = String.format("%s取消", desc)
            }
        }
        desc = String.format(locale = Locale.CHINA,
            "%s\n动作发生位置是：横坐标%f，纵坐标%f，压力为%f",
            desc, event.x, event.y, event.pressure
        )
        binding.tvTouch.text = desc
        return super.onTouchEvent(event)
    }
}