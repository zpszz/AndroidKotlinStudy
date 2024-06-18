package com.jpc.chapter11.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityClickLongBinding
import com.jpc.chapter11.widget.ClickView

class ClickLongActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClickLongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClickLongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.click_long)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 设置点击视图的手势抬起监听器
        binding.cvGesture.setLiftListener(object : ClickView.LiftListener{ // 创建匿名内部类实现LiftListener接口
            override fun onLift(timeInterval: Long, pressure: Float) {
                val gesture = if (timeInterval > 500) "长按" else "点击"
                val desc = "本次按压时长为${timeInterval}毫秒，属于${gesture}动作。\n按压的压力峰值为${pressure}"
                binding.tvDesc.text = desc
            }
        })
    }
}