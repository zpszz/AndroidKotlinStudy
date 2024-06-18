package com.jpc.chapter11.bean

import android.graphics.PointF

// 定义一个路径位置实体类，包括上个落点的横纵坐标，以及下个落点的横纵坐标
class PathPosition {
    var prePos: PointF? = null // 上个落点的横纵坐标
    var nextPos: PointF? = null // 下个落点的横纵坐标
}