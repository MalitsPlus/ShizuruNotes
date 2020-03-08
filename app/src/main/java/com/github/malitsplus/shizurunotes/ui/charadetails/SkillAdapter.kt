package com.github.malitsplus.shizurunotes.ui.charadetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.data.Skill
import com.github.malitsplus.shizurunotes.databinding.ListItemSkillBinding
import com.github.malitsplus.shizurunotes.ui.shared.SharedViewModelChara
import java.util.*

class SkillAdapter(
    private val sharedChara: SharedViewModelChara
) : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {

    var itemList: List<Skill> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SkillViewHolder {
        val binding = DataBindingUtil.inflate<ListItemSkillBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_skill, parent, false
        )
        return SkillViewHolder(
            binding
        )
    }

    //填充每个item的视图
    override fun onBindViewHolder(
        holder: SkillViewHolder,
        position: Int
    ) {
        with(holder.binding){
            skill = itemList[position].also { s ->
                if (s.friendlyMinionList.isNotEmpty()){
                    minionButton.visibility = View.VISIBLE
                    minionButton.setOnClickListener {
                        sharedChara.selectedMinion = s.friendlyMinionList
                        it.findNavController()
                            .navigate(CharaDetailsFragmentDirections
                                .actionNavCharaDetailsToNavMinion()
                            )
                    }
                }
            }
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun update(itemList: List<Skill>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class SkillViewHolder internal constructor(val binding: ListItemSkillBinding) :
        RecyclerView.ViewHolder(binding.root)

}