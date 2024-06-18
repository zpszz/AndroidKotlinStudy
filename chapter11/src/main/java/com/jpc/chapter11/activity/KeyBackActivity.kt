package com.jpc.chapter11.activity

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityKeyBackBinding

class KeyBackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeyBackBinding
    private var needExit: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKeyBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.key_back)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * 重写onKeyDown方法实现拦截返回键
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(needExit){
                finish()
                return true
            }
            needExit = true
            Toast.makeText(this, "再按一下返回键退出", Toast.LENGTH_SHORT).show()
            return true
        }else{
            return super.onKeyDown(keyCode, event)
        }
    }

    /**
     * 重写onBackPressed方法实现拦截返回键
     * 方法已过时
     */
//    override fun onBackPressed() {
//        super.onBackPressed()
//        if (needExit){
//            finish()
//        }else{
//            needExit = true
//            Toast.makeText(this, "再按一下返回键退出", Toast.LENGTH_SHORT).show()
//        }
//    }
}