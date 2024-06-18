package com.jpc.chapter11.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityKeySoftBinding

class KeySoftActivity : AppCompatActivity(), OnKeyListener{
    private lateinit var binding: ActivityKeySoftBinding
    private var desc: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKeySoftBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.key_soft)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.etKeySoft.setOnKeyListener(this)
    }

    /**
     * 实现OnKeyListener接口，实现OnKey方法，监听软键盘按键事件
     * @param event 按键事件  KeyEvent.ACTION_DOWN按下事件， KeyEvent.ACTION_UP 松开
     * @return true 表示处理完了不再输入字符， false表示继续输入
     */
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN){
            desc = "软按键编码是$keyCode, 动作是按下"
            when(keyCode){
                KeyEvent.KEYCODE_ENTER -> desc = "$desc, 按键是回车键"
                KeyEvent.KEYCODE_DEL -> desc = "$desc, 按键是删除键"
                KeyEvent.KEYCODE_SEARCH -> desc = "$desc, 按键是搜索键"
                KeyEvent.KEYCODE_BACK -> {
                    desc = "$desc, 按键是返回键"
                    // 延迟3s返回
                    val handler = Handler(Looper.getMainLooper()) // 创建一个Handler对象，绑定主线程的Looper
                    // postDelayed方法是在指定的时间后执行一个任务，第一个参数是Runnable对象，第二个参数是延迟的时间
                    handler.postDelayed({
                        finish() // 3s后关闭当前Activity
                    }, 3000L)
                }
                KeyEvent.KEYCODE_VOLUME_UP -> desc = "$desc, 按键是音量增大键"
                KeyEvent.KEYCODE_VOLUME_DOWN -> desc = "$desc, 按键是音量减小键"
            }
            desc = "$desc\n"
            binding.tvResult.text = desc
            return true
        }else{
            return false
        }
    }
}