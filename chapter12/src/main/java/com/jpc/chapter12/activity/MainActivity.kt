package com.jpc.chapter12.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter12.R
import com.jpc.chapter12.databinding.ActivityMainBinding

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
        binding.btnFrameAnim.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.btnFrameAnim.id -> {
                val intent = Intent(this, FrameAnimActivity::class.java)
                startActivity(intent)
            }
        }
    }
}