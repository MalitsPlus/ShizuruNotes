package com.github.malitsplus.shizurunotes.ui.charalist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.databinding.ListItemCharaBinding
import com.github.malitsplus.shizurunotes.ui.SharedViewModel
import com.github.malitsplus.shizurunotes.ui.ViewPagerFragmentDirections
import java.util.*

class CharaListAdapter(
    private val mContext: Context,
    private val sharedViewModel: SharedViewModel
) :
    RecyclerView.Adapter<CharaListAdapter.CharaListViewHolder>() {
    private var charaList: List<Chara> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharaListViewHolder {
        val binding = DataBindingUtil.inflate<ListItemCharaBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_chara, parent, false
        )
        //向每个item设置点击监听事件
        binding.clickListener = View.OnClickListener { v: View? ->
            sharedViewModel.selectedChara = binding.chara
            val action: NavDirections =
                ViewPagerFragmentDirections.actionNavViewPagerToNavCharaDetails()
                //CharaListFragmentDirections.actionNavCharaToNavCharaDetails()
            Navigation.findNavController(v!!).navigate(action)
        }
        return CharaListViewHolder(
            binding
        )
    }

    //填充每个item的视图
    override fun onBindViewHolder(
        holder: CharaListViewHolder,
        position: Int
    ) {
        holder.binding.chara = charaList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return charaList.size
    }

    fun update(charaList: List<Chara>) {
        this.charaList = charaList
        notifyDataSetChanged()
    }


    class CharaListViewHolder internal constructor(val binding: ListItemCharaBinding) :
        RecyclerView.ViewHolder(binding.root)
}