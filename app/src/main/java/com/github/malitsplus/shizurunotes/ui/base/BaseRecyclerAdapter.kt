package com.github.malitsplus.shizurunotes.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/***
 * T: listItem实例
 * V: 布局Binding
 */
abstract class BaseRecyclerAdapter<T, V: ViewDataBinding>(private val itemLayout: Int) : RecyclerView.Adapter<BaseRecyclerAdapter.VH<V>>() {

    var itemList = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<V>{
        val binding = DataBindingUtil.inflate<V>(LayoutInflater.from(parent.context), itemLayout, parent, false)
        return VH(binding)
    }

    abstract override fun onBindViewHolder(holder: VH<V>, position: Int)

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun update(itemList: MutableList<T>){
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class VH<V: ViewDataBinding> internal constructor(
        val binding: V
    ) : RecyclerView.ViewHolder(binding.root)
}