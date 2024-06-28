package com.jpc.chapter12.activity

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter12.R
import com.jpc.chapter12.databinding.ActivityFrameAnimBinding

class FrameAnimActivity : AppCompatActivity() {
    private val binding: ActivityFrameAnimBinding by lazy { ActivityFrameAnimBinding.inflate(layoutInflater) }
    private lateinit var adFrame: AnimationDrawable // 声明一个帧动画对象
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_frame_anim)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ivFrameAnim.setOnClickListener {
            if (adFrame.isRunning){
                adFrame.stop()
            }else{
                adFrame.start()
            }
        }
        // showFrameAnimByCode()
        showFrameAnimByXml()
    }
    // 在代码中创建帧动画
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showFrameAnimByCode(){
        adFrame = AnimationDrawable() // 创建一个帧动画图形
        // 添加图片到列表中
        adFrame.addFrame(getDrawable(R.drawable.flow_p1)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p2)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p3)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p4)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p5)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p6)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p7)!!, 50)
        adFrame.addFrame(getDrawable(R.drawable.flow_p8)!!, 50)
        // 设置帧动画是否只播放一次
        adFrame.isOneShot = false // false表示播放多次
        // 设置ImageView的图形为帧动画
        binding.ivFrameAnim.setImageDrawable(adFrame)
        // 开始播放帧动画
        adFrame.start()
    }

    // 使用xml方式创建帧动画，animation-list
    private fun showFrameAnimByXml(){
        // 设置图像视图的图形来源为xml定义的帧动画
        binding.ivFrameAnim.setImageResource(R.drawable.frame_anim)
        // 从图像视图对象中获取帧动画
        adFrame = binding.ivFrameAnim.drawable as AnimationDrawable
        adFrame.start()
    }
}