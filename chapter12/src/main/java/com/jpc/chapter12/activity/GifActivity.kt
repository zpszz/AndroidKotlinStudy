package com.jpc.chapter12.activity

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jpc.chapter12.R
import com.jpc.chapter12.databinding.ActivityGifBinding
import com.jpc.chapter12.util.GifImage

class GifActivity : AppCompatActivity() {
    private val binding: ActivityGifBinding by lazy { ActivityGifBinding.inflate(layoutInflater) }
    private val typeArray = arrayOf("显示GIF动图", "显示WebP动图", "显示HEIF图片", "显示AVIF图像")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_gif)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    // 初始化图像类型的下拉框
    private fun initTypeSpinner(){
        val arrayAdapter = ArrayAdapter(this, R.layout.item_select, typeArray)
        binding.spType.apply {
            prompt = "请选择图像类型"
            adapter = arrayAdapter
            onItemSelectedListener = ImageTypeListener()
            setSelection(0)
        }
    }
    inner class ImageTypeListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, arg2: Int, arg3: Long) {
            if (arg2 == 0) {
                // 利用Android9新增的AnimatedImageDrawable显示GIF动画
                showAnimateDrawable(R.drawable.happy)
                //showGifMovie(R.drawable.happy) // 通过Movie类播放动图
            } else if (arg2 == 1) {
                //showAnimateDrawable(R.drawable.world_cup_2014)
            } else if (arg2 == 2) {
                //showSpecial(R.raw.lotus) // 显示Heif图片（扩展名为heif或者heic）
            } else if (arg2 == 3) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    //showSpecial(R.raw.app) // 显示Avif图片（扩展名为avif）
                } else {
//                    Toast.makeText(
//                        this,
//                        "显示AVIF图片需要Android12及更高版本",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
        }
        override fun onNothingSelected(arg0: AdapterView<*>?) {}
    }
    // 显示GIF动画
    @SuppressLint("SetTextI18n")
    private fun showGifAnimationOld(imageId: Int){
        binding.tvInfo.text = ""
        // 从资源文件中获取输入流对象
        val inputStream = resources.openRawResource(imageId)
        val gifImage = GifImage() // 创建一个GIF图像对象
        val code = gifImage.read(inputStream) // 从输入流中读取GIF数据
        if (code == GifImage.STATUS_OK){
            val frames = gifImage.getFrames()
            // 创建一个帧动画
            val adGif = AnimationDrawable()
            frames?.let {
                for (frame in it){
                    // 把Bitmap位图转换为Drawable图形格式
                    val bitmapDrawable = BitmapDrawable(resources, frame.image)
                    // 给帧动画添加指定图形，以及该帧的播放延迟
                    adGif.addFrame(bitmapDrawable, frame.delay)
                }
            }
            adGif.isOneShot = false
            binding.ivGif.setImageDrawable(adGif)
            adGif.start()
        }else if(code == GifImage.STATUS_FORMAT_ERROR){
            Toast.makeText(this, "图片格式不是GIF格式", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "图片读取失败", Toast.LENGTH_SHORT).show()
        }
    }

    fun showAnimateDrawable(imageId: Int){
        try {
            // 利用Android9新增的ImageDecoder获取图像来源
            val source = ImageDecoder.createSource(resources, imageId)
            showImageSource(source) // 显示指定来源的图像
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun showImageSource(source: ImageDecoder.Source) {
        // 从数据源解码获取图形信息
        val drawable = ImageDecoder.decodeDrawable(source){ decoder, info, source1 ->
            // 获取图形信息的媒体类型，判断是否动图
            val desc = "该图片类型为${info.mimeType}，它${ if (info.isAnimated) "是" else "不是" }动图"
            binding.tvInfo.text = desc
        }
        binding.ivGif.setImageDrawable(drawable) // 设置图像视图的图形对象
        if (drawable is Animatable){ // 如果是动画就播放
            (binding.ivGif.drawable as Animatable).start()
        }
    }
}