package com.jpc.chapter11.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityEventInterceptBinding
import com.jpc.chapter11.widget.InterceptLayout

class EventInterceptActivity : AppCompatActivity(), InterceptLayout.InterceptListener{
    private lateinit var binding: ActivityEventInterceptBinding
    private var desc_yes: String = ""
    private var desc_no: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEventInterceptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_intercept)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 设置拦截布局的事件拦截监听器
        binding.ilInterceptYes.setInterceptListener(this)
        binding.btnInterceptYes.setOnClickListener{
            desc_yes = "点击了按钮"
            binding.tvInterceptYes.text = desc_yes
        }
        binding.btnInterceptNo.setOnClickListener{
            desc_no = "点击了按钮"
            binding.tvInterceptNo.text = desc_no
        }

    }
    override fun onIntercept() {
        desc_yes = "触摸动作被拦截，按钮点击不了"
        binding.tvInterceptYes.text = desc_yes
    }
}