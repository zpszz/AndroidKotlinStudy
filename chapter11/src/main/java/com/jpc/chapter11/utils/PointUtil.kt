package com.jpc.chapter11.utils

import android.graphics.PointF
import kotlin.math.atan
import kotlin.math.sqrt

object PointUtil {
    // 计算两个坐标点之间的距离
    fun distance(p1: PointF, p2: PointF): Float {
        val offsetX = p2.x - p1.x
        val offsetY = p2.y - p1.y
        return sqrt((offsetX * offsetX + offsetY * offsetY).toDouble()).toFloat()
    }

    // 计算两个坐标点构成的角度
    fun degree(p1: PointF, p2: PointF): Int {
        return (atan(((p2.y - p1.y) / (p2.x - p1.x)).toDouble()) / Math.PI * 180).toInt()
    }
}
