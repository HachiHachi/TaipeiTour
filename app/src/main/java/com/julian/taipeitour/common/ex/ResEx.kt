package com.julian.taipeitour.common.ex

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.julian.taipeitour.BaseApplication

object ResEx {

    //drawable
    fun Any.drawable(@DrawableRes idRes: Int, context: Context = BaseApplication.instance): Drawable =
        ContextCompat.getDrawable(context, idRes) as Drawable

    fun Any.string(@StringRes idRes: Int, context: Context = BaseApplication.instance): String =
        context.getString(idRes)

}