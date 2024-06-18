package com.jpc.chapter11.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityEventDispatchBinding
import com.jpc.chapter11.widget.NotDispatchLayout
import java.util.Date

class EventDispatchActivity : AppCompatActivity(), NotDispatchLayout.NotDispatchListener{
    private lateinit var binding: ActivityEventDispatchBinding
    private var desc_yes: String = ""
    private var desc_not: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEventDispatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_dispatch)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 设置不分发布局的事件分发监听器
        binding.ndlDispatchNot.setNotDispatchListener(this)
        binding.btnDispatchYes.setOnClickListener{
            desc_yes = "$desc_yes, ${Date().time},您点击了按钮\n"
            binding.tvDispatchYes.text = desc_yes
        }
        binding.btnDispatchNot.setOnClickListener{
            desc_not = "$desc_not, ${Date().time},您点击了按钮\n"
            binding.tvDispatchNot.text = desc_not
        }
    }
    // 分发触摸事件时触发
    override fun onNotDispatch() {
        desc_not = "${desc_not},${Date().time}触摸动作未分发，按钮点击不了了\n"
        binding.tvDispatchNot.text = desc_not
    }
}