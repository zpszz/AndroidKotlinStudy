package com.jpc.chapter11.utils

import android.content.Context
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.LinearLayout

object Utils {
    // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    fun dip2px(context: Context, dpValue: Float): Int {
        // 获取当前手机的像素密度
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt() // 四舍五入取整
    }

    // 根据手机的分辨率从 px(像素) 的单位 转成为 dp
    fun px2dip(context: Context, pxValue: Float): Int {
        // 获取当前手机的像素密度
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt() // 四舍五入取整
    }

    // 获得屏幕的宽度
    fun getScreenWidth(ctx: Context): Int {
        return ctx.resources.displayMetrics.widthPixels
        //        int screenWidth;
//        // 从系统服务中获取窗口管理器
//        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // 获取当前屏幕的四周边界
//            Rect rect = wm.getCurrentWindowMetrics().getBounds();
//            screenWidth = rect.width();
//        } else {
//            DisplayMetrics dm = new DisplayMetrics();
//            // 从默认显示器中获取显示参数保存到dm对象中
//            wm.getDefaultDisplay().getMetrics(dm);
//            screenWidth = dm.widthPixels;
//        }
//        return screenWidth; // 返回屏幕的宽度数值
    }

    // 获得屏幕的高度
    fun getScreenHeight(ctx: Context): Int {
        return ctx.resources.displayMetrics.heightPixels
        //        int screenHeight;
//        // 从系统服务中获取窗口管理器
//        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // 获取当前屏幕的四周边界
//            Rect rect = wm.getCurrentWindowMetrics().getBounds();
//            screenHeight = rect.height();
//        } else {
//            DisplayMetrics dm = new DisplayMetrics();
//            // 从默认显示器中获取显示参数保存到dm对象中
//            wm.getDefaultDisplay().getMetrics(dm);
//            screenHeight = dm.heightPixels;
//        }
//        return screenHeight; // 返回屏幕的高度数值
    }

    // 计算指定线性布局的实际高度
    fun getRealHeight(child: View): Int {
        val llayout = child as LinearLayout
        // 获得线性布局的布局参数
        var params = llayout.layoutParams
        if (params == null) {
            params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        // 获得布局参数里面的宽度规格
        val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width)
        val heightSpec = if (params.height > 0) { // 高度大于0，说明这是明确的dp数值
            // 按照精确数值的情况计算高度规格
            MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY)
        } else { // MATCH_PARENT=-1，WRAP_CONTENT=-2，所以二者都进入该分支
            // 按照不确定的情况计算高度规则
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        }
        llayout.measure(widthSpec, heightSpec) // 重新丈量线性布局的宽高
        // 获得并返回线性布局丈量之后的高度数值。调用getMeasuredWidth方法可获得宽度数值
        return llayout.measuredHeight // 获取视图的实际高度
    }
}

