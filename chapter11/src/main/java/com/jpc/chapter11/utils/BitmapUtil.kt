package com.jpc.chapter11.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object BitmapUtil {
    private const val TAG = "BitmapUtil"

    // 把位图数据保存到指定路径的图片文件
    fun saveImage(path: String?, bitmap: Bitmap) {
        // 根据指定的文件路径构建文件输出流对象
        try {
            FileOutputStream(path).use { fos ->
                // 把位图数据压缩到文件输出流中
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 获得旋转角度之后的位图对象
    fun getRotateBitmap(bitmap: Bitmap, rotateDegree: Float): Bitmap {
        val matrix = Matrix() // 创建操作图片用的矩阵对象
        matrix.postRotate(rotateDegree) // 执行图片的旋转动作
        // 创建并返回旋转后的位图对象
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, false
        )
    }

    // 获得比例缩放之后的位图对象
    private fun getScaleBitmap(bitmap: Bitmap, scaleRatio: Double): Bitmap {
        val matrix = Matrix() // 创建操作图片用的矩阵对象
        matrix.postScale(scaleRatio.toFloat(), scaleRatio.toFloat())
        // 创建并返回缩放后的位图对象
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, false
        )
    }

    // 获得自动缩小后的位图对象
    fun getAutoZoomImage(ctx: Context, uri: Uri): Bitmap? {
        Log.d(TAG, "getAutoZoomImage uri=$uri")
        var zoomBitmap: Bitmap? = null
        // 打开指定uri获得输入流对象
        try {
            ctx.contentResolver.openInputStream(uri).use { `is` ->
                // 从输入流解码得到原始的位图对象
                val originBitmap = BitmapFactory.decodeStream(`is`)
                val ratio = originBitmap.width / 2000 + 1
                // 获得比例缩放之后的位图对象
                zoomBitmap = getScaleBitmap(originBitmap, 1.0 / ratio)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return zoomBitmap
    }

    // 通知相册来了张新图片， 方法过时
    fun notifyPhotoAlbum1(ctx: Context, filePath: String) {
        try {
            val fileName = filePath.substring(filePath.lastIndexOf("/") + 1)
            MediaStore.Images.Media.insertImage(
                ctx.contentResolver,
                filePath, fileName, null
            )
            val uri = Uri.parse("file://$filePath")
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
            ctx.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 通知相册保存新图片
    fun notifyPhotoAlbum(context: Context, filePath: String) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filePath.substring(filePath.lastIndexOf("/") + 1))
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val contentUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val uri = context.contentResolver.insert(contentUri, values)

        uri?.let {
            context.contentResolver.openOutputStream(it).use { outputStream ->
                val imageFile = File(filePath)
                val inputStream = FileInputStream(imageFile)
                outputStream?.let { outStream ->
                    inputStream.copyTo(outStream)
                    outStream.flush()
                    outStream.close()
                }
                inputStream.close()
            }

            values.clear()
            context.contentResolver.update(it, values, null, null)
        }
    }

}
