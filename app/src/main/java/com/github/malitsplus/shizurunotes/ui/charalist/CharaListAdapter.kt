package com.github.malitsplus.shizurunotes.ui.charalist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Chara
import com.github.malitsplus.shizurunotes.databinding.ListItemCharaBinding
import com.github.malitsplus.shizurunotes.ui.BottomNaviFragmentDirections
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import java.util.*

class CharaListAdapter(
    private val sharedViewModelChara: SharedViewModelChara
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
        binding.clickListener = View.OnClickListener { v: View ->
            sharedViewModelChara.mSetSelectedChara(binding.chara)
            sharedViewModelChara.backFlag = false
            var extras: FragmentNavigator.Extras
            binding.apply {
                chara?.charaId.also {
                    extras = FragmentNavigatorExtras(
                        itemChara to "transItem_$it"
                    )
                }
            }

            val action = BottomNaviFragmentDirections
                .actionNavBottomNavigationToNavCharaDetails()
                .setCharaId(binding.chara!!.charaId)

            v.findNavController().navigate(action, extras)
        }
        return CharaListViewHolder(binding)
    }


    //填充每个item的视图
    override fun onBindViewHolder(
        holder: CharaListViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            chara = charaList[position]
            itemChara.transitionName = "transItem_${charaList[position].charaId}"
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return charaList.size
    }

    fun update(charaList: List<Chara>) {
        this.charaList = charaList
        notifyDataSetChanged()

    }


    class CharaListViewHolder internal constructor(
        val binding: ListItemCharaBinding
    ) : RecyclerView.ViewHolder(binding.root)
}