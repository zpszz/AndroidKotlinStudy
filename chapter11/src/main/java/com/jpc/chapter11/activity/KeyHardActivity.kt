package com.jpc.chapter11.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityKeyHardBinding

class KeyHardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeyHardBinding
    private var desc: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKeyHardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.key_hard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * @param keyCode 按键的编码
     * @param event 按键事件
     * @return true表示不再响应系统动作 false表示继续响应
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        desc = "${desc}物理按键的编码是${keyCode}"
        when(keyCode){
            KeyEvent.KEYCODE_BACK ->{
                desc = "$desc, 按下返回键"
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    finish()
                }, 3000L)
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> desc = "$desc, 按下音量增大键"
            KeyEvent.KEYCODE_VOLUME_UP -> desc = "$desc, 按下音量减小键"
        }
        desc = "$desc\n"
        binding.tvResult.text = desc
        return true
    }
}