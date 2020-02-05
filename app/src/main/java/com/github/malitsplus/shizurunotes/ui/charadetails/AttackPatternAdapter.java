package com.github.malitsplus.shizurunotes.ui.charadetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.data.AttackPattern;
import com.github.malitsplus.shizurunotes.databinding.ListItemAttackPatternBinding;

import java.util.ArrayList;
import java.util.List;

public class AttackPatternAdapter extends RecyclerView.Adapter<AttackPatternAdapter.AttackPatternViewHolder> {

    private Context mContext;
    private List<AttackPattern.AttackPatternItem> itemList = new ArrayList<>();

    public AttackPatternAdapter(Context context){
        mContext = context;
    }


    @Override
    @NonNull
    public AttackPatternViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemAttackPatternBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_attack_pattern, parent, false);

        return new AttackPatternViewHolder(binding);

    }

    //填充每个item的视图
    @Override
    public void onBindViewHolder(AttackPatternViewHolder holder, int position){
        holder.getBinding().setPatternItem(itemList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    public void update(List<AttackPattern.AttackPatternItem> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public static class AttackPatternViewHolder extends RecyclerView.ViewHolder{

        private ListItemAttackPatternBinding binding;
        AttackPatternViewHolder(ListItemAttackPatternBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        ListItemAttackPatternBinding getBinding(){
            return binding;
        }

    }
}
