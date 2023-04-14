package com.julian.taipeitour.common.ex

import android.content.res.Resources

object ViewEx {

    fun Int.toToPixel(resources: Resources): Int = resources.getDimension(this).toInt()
}