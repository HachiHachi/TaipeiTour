package com.julian.taipeitour.common.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.julian.taipeitour.R

abstract class EmptyViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    companion object {
        const val ITEM_TYPE_EMPTY = -1
        const val ITEM_TYPE_DATA = 100
    }

    abstract fun getDataCount():Int

    override fun getItemViewType(position: Int): Int {
        if (getDataCount()==0) {
            return ITEM_TYPE_EMPTY
        } else
            return ITEM_TYPE_DATA
    }

    override fun getItemCount(): Int {
        if (getDataCount()==0) {
            return 1
        }
        return getDataCount()
    }

    fun getEmptyView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_empty_view,parent,false)
    }

    inner class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun setIcon(@DrawableRes res:Int) {
            imgIcon.setImageResource(res)
            imgIcon.visibility = View.VISIBLE
        }
        fun setTitle(tStr:String) {
            title.text = tStr
            title.visibility = View.VISIBLE
        }
        fun setMessage(mStr:String) {
            msg.text = mStr
            msg.visibility = View.VISIBLE
        }
//        fun setButtonTxt(bStr:String) {
//            button.text = bStr
//            button.visibility = View.VISIBLE
//        }

        var imgIcon: ImageView = itemView.findViewById(R.id.iv_no_data)
        var title: TextView = itemView.findViewById(R.id.tv_no_data_title)
        var msg: TextView = itemView.findViewById(R.id.tv_no_data_msg)

    }


}