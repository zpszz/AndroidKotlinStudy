package com.jpc.chapter11.activity

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter11.R
import com.jpc.chapter11.databinding.ActivitySignatureBinding
import com.jpc.chapter11.utils.BitmapUtil
import java.util.Date

class SignatureActivity : AppCompatActivity(), OnClickListener{
    private lateinit var binding: ActivitySignatureBinding
    private var mImagePath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignatureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_signature)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSaveSignature.setOnClickListener(this)
        binding.btnBeginSignature.setOnClickListener(this)
        binding.btnResetSignature.setOnClickListener(this)
        binding.btnRevokeSignature.setOnClickListener(this)
        binding.btnEndSignature.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.btnSaveSignature.id -> {
                if (mImagePath.isNotBlank()) {
                    Toast.makeText(this, "图片已保存至相册，请查看", Toast.LENGTH_SHORT).show()
                    BitmapUtil.notifyPhotoAlbum(this, mImagePath)
                }else{
                    Toast.makeText(this, "请先开始签名再结束签名", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            binding.btnBeginSignature.id -> {
                //binding.viewSignature.isDrawingCacheEnabled = true
            }
            binding.btnResetSignature.id -> {
                // 重置按钮，清空签名
                binding.viewSignature.clear()
            }
            binding.btnRevokeSignature.id -> {
                // 回退按钮
                binding.viewSignature.revoke()
            }
            binding.btnEndSignature.id ->{
                val bitmap = binding.viewSignature.captureView()
                mImagePath = "${getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/${Date().time}.jpg"
                BitmapUtil.saveImage(mImagePath, bitmap)
                binding.ivSignatureNew.setImageURI(Uri.parse(mImagePath))

//                if (!binding.viewSignature.isDrawingCacheEnabled){
//                    Toast.makeText(this, "请先开始签名", Toast.LENGTH_SHORT).show()
//                }else{
//                    val bitmap = binding.viewSignature.drawingCache
//                    mImagePath = "${getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/${Date().time}.jpg"
//                    BitmapUtil.saveImage(mImagePath, bitmap)
//                    binding.ivSignatureNew.setImageURI(Uri.parse(mImagePath))
//                    Handler(Looper.getMainLooper()).postDelayed({
//                          binding.viewSignature.isDrawingCacheEnabled = false
//                          binding.viewSignature.isDrawingCacheEnabled = true
//                    }, 100)
//                }
            }
        }
    }
}