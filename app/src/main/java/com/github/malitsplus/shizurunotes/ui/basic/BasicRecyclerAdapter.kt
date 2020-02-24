package com.github.malitsplus.shizurunotes.ui.basic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BasicRecyclerAdapter<T, V: ViewDataBinding>(private val itemLayout: Int) : RecyclerView.Adapter<BasicRecyclerAdapter.VH<V>>() {

    var itemList: List<T> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<V>{
        val binding = DataBindingUtil.inflate<V>(LayoutInflater.from(parent.context), itemLayout, parent, false)
        return VH(binding)
    }

    abstract override fun onBindViewHolder(holder: VH<V>, position: Int)

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun update(itemList: List<T>){
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class VH<V: ViewDataBinding> internal constructor(
        val binding: V
    ) : RecyclerView.ViewHolder(binding.root)
}