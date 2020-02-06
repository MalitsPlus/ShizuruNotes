package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.data.Skill;
import com.github.malitsplus.shizurunotes.databinding.ListItemSkillBinding;

import java.util.ArrayList;
import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {
    private Context mContext;
    private List<Skill> itemList = new ArrayList<>();

    public SkillAdapter(Context context){
        mContext = context;
    }


    @Override
    @NonNull
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSkillBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_skill, parent, false);

        return new SkillViewHolder(binding);

    }

    //填充每个item的视图
    @Override
    public void onBindViewHolder(SkillViewHolder holder, int position){
        holder.getBinding().setSkill(itemList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    public void update(List<Skill> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public static class SkillViewHolder extends RecyclerView.ViewHolder{

        private ListItemSkillBinding binding;
        SkillViewHolder(ListItemSkillBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        ListItemSkillBinding getBinding(){
            return binding;
        }

    }
}
