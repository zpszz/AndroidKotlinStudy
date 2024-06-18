package com.jpc.chapter11.activity

import android.graphics.PointF
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivitySlideDirectionBinding
import com.jpc.chapter11.widget.SingleTouchView
import kotlin.math.abs

class SlideDirectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySlideDirectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySlideDirectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.slide_direction)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.stvGesture.setFlipListener(object : SingleTouchView.FlipListener{
            override fun onFlipFinish(beginPos: PointF, endPos: PointF) {
                val offsetX = abs(beginPos.x - endPos.x)
                val offsetY = abs(beginPos.y - endPos.y)
                val direction = if(offsetX > offsetY){
                    if(endPos.x > beginPos.x) "向右" else "向左"
                }else if(offsetX < offsetY){
                    if(endPos.y > beginPos.y) "向下" else "向上"
                }else{
                    "对角线"
                }
                val desc = "本次手势为${direction}滑动"
                binding.tvDesc.text = desc
            }
        })
    }
}