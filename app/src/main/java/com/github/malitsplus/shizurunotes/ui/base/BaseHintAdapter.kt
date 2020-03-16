package com.github.malitsplus.shizurunotes.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.databinding.ItemHintTextBinding
import java.lang.IllegalArgumentException

abstract class BaseHintAdapter<V: ViewDataBinding>(
    private val mContext: Context,
    private val itemLayout: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HINT_TEXT = 1
        const val INSTANCE_ITEM = 2
    }

    var itemList = listOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            HINT_TEXT -> {
                val binding = DataBindingUtil.inflate<ItemHintTextBinding>(LayoutInflater.from(parent.context), R.layout.item_hint_text, parent, false)
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

    fun update(items: List<Any>) {
        itemList = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]) {
            is String -> HINT_TEXT
            else -> INSTANCE_ITEM
        }
    }

    class HintTextViewHolder internal constructor(
        val binding: ItemHintTextBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class InstanceViewHolder internal constructor(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}