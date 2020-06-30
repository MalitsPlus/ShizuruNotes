package com.github.malitsplus.shizurunotes.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewTypeAdapter<E : ViewType<*>>(
    private var list: MutableList<E> = mutableListOf(),
    private val onItemActionListener: OnItemActionListener? = null
) : RecyclerView.Adapter<ViewTypeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTypeHolder {
        return ViewTypeHolder.create(parent, viewType, onItemActionListener)
    }

    override fun onBindViewHolder(holder: ViewTypeHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemViewType(position: Int): Int = list[position].layoutId

    override fun getItemCount(): Int = list.size

    fun setList(list: List<E>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setUpdatedList(list: MutableList<E>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun insertElement(data: E, position: Int? = null) {
        if (position != null) {
            this.list.add(position, data)
            notifyItemInserted(position)
        } else {
            this.list.add(data)
            notifyItemInserted(this.list.size - 1)
        }
    }

    fun updateElement(data: E, position: Int) {
        this.list[position] = data
        notifyItemChanged(position)
    }

    fun updateElements(data: List<E>, startIndex: Int) {
        var start = startIndex
        for (i in data.indices) {
            if (start >= this.list.size) {
                this.list.add(data[i])
            } else {
                this.list[start] = data[i]
            }
            start++
        }
        notifyItemRangeChanged(startIndex, data.size)
    }

    fun insertElements(data: List<E>, position: Int? = null) {
        if (position != null) {
            this.list.addAll(position, data)
            notifyItemRangeInserted(position, data.size)
        } else {
            val index = this.list.size - 1
            this.list.addAll(data)
            notifyItemRangeInserted(index, this.list.size - 1)
        }
    }

    fun removeElements(startIndex: Int, endIndex: Int = this.list.size - 1) {
        val iterator = this.list.listIterator(startIndex)
        var end = endIndex
        while (iterator.hasNext()) {
            iterator.next()
            if (startIndex <= end) {
                iterator.remove()
                end--
            } else {
                break
            }
        }
        notifyItemRangeRemoved(startIndex, endIndex - startIndex)
    }

    fun removeElement(index: Int) {
        if (index < list.size) {
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateLastElement(data: E) {
        this.list[this.list.size - 1] = data
        notifyItemChanged(this.list.size - 1)
    }
}
