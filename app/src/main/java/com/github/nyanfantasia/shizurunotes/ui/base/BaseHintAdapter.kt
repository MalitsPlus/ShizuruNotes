package com.github.nyanfantasia.shizurunotes.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

abstract class BaseHintAdapter<V: ViewDataBinding, H: ViewDataBinding>(
    private val mContext: Context?,
    private val itemLayout: Int,
    private val hintLayout: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HINT_TEXT = 1
        const val INSTANCE_ITEM = 2
    }

    var itemList = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            HINT_TEXT -> {
                val binding = DataBindingUtil.inflate<H>(LayoutInflater.from(parent.context), hintLayout, parent, false)
                HintTextViewHolder(binding)
            }
            INSTANCE_ITEM -> {
                val binding = DataBindingUtil.inflate<V>(LayoutInflater.from(parent.context), itemLayout, parent, false)
                InstanceViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun update(items: MutableList<Any>) {
        itemList = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]) {
            is String -> HINT_TEXT
            is Int -> HINT_TEXT
            else -> INSTANCE_ITEM
        }
    }

    class HintTextViewHolder internal constructor(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class InstanceViewHolder internal constructor(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}