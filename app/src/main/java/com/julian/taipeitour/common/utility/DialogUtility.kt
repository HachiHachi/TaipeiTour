package com.julian.taipeitour.common.utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.julian.taipeitour.R

object DialogUtility {

    fun progressDialog(context: Context): AlertDialog {
        val alertDialog: AlertDialog
        val item = LayoutInflater.from(context).inflate(R.layout.item_progress, null)

        alertDialog = AlertDialog.Builder(context, R.style.ProgressDialogStyle)
            .setView(item)
            .setCancelable(false)
            .create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return alertDialog
    }

}