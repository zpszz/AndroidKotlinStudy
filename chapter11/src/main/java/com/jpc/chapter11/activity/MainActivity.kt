package com.jpc.chapter11.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener{
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnKeySoft.setOnClickListener(this)
        binding.btnKeyHard.setOnClickListener(this)
        binding.btnBackPress.setOnClickListener(this)
        binding.btnEventDispatch.setOnClickListener(this)
        binding.btnEventIntercept.setOnClickListener(this)
        binding.btnTouchSingle.setOnClickListener(this)
        binding.btnTouchMultiple.setOnClickListener(this)
        binding.btnSignature.setOnClickListener(this)
        binding.btnClickLong.setOnClickListener(this)
        binding.btnSlideDirection.setOnClickListener(this)
        binding.btnScaleRotate.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnKeySoft.id ->{
                val intent = Intent(this, KeySoftActivity::class.java)
                startActivity(intent)
            }
            binding.btnKeyHard.id ->{
                val intent = Intent(this, KeyHardActivity::class.java)
                startActivity(intent)
            }
            binding.btnBackPress.id ->{
                val intent = Intent(this, KeyBackActivity::class.java)
                startActivity(intent)
            }
            binding.btnEventDispatch.id ->{
                val intent = Intent(this, EventDispatchActivity::class.java)
                startActivity(intent)
            }
            binding.btnEventIntercept.id ->{
                val intent = Intent(this, EventInterceptActivity::class.java)
                startActivity(intent)
            }
            binding.btnTouchSingle.id -> {
                val intent = Intent(this, TouchSingleActivity::class.java)
                startActivity(intent)
            }
            binding.btnTouchMultiple.id -> {
                val intent = Intent(this, TouchMultipleActivity::class.java)
                startActivity(intent)
            }
            binding.btnSignature.id -> {
                val intent = Intent(this, SignatureActivity::class.java)
                startActivity(intent)
            }
            binding.btnClickLong.id -> {
                val intent = Intent(this, ClickLongActivity::class.java)
                startActivity(intent)
            }
            binding.btnSlideDirection.id -> {
                val intent = Intent(this, SlideDirectionActivity::class.java)
                startActivity(intent)
            }
            binding.btnScaleRotate.id -> {
                val intent = Intent(this, ScaleRotateActivity::class.java)
                startActivity(intent)
            }
        }
    }
}