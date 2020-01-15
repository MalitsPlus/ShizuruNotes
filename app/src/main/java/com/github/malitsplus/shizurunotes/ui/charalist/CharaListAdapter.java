package com.github.malitsplus.shizurunotes.ui.charalist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.JsonUtils;
import com.github.malitsplus.shizurunotes.databinding.ListItemCharaBinding;
import com.github.malitsplus.shizurunotes.ui.charadetails.CharaDetailsViewModel;
import java.util.List;

public class CharaListAdapter extends RecyclerView.Adapter<CharaListAdapter.CharaListViewHolder> {

    private Context mContext;
    private List<CharaDetailsViewModel> charaDetailsViewModels;

    public CharaListAdapter(Context context){
        mContext = context;
    }


    @Override
    @NonNull
    public CharaListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCharaBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_chara, parent, false);

        //向每个item设置点击监听事件
        binding.setClickListener((v) -> {
            NavDirections action = CharaListFragmentDirections.actionNavCharaToNavCharaDetails(
                    JsonUtils.getJsonFromBean(binding.getCharaViewModel().chara.getValue()));
            Navigation.findNavController(v).navigate(action);
        });

        return new CharaListViewHolder(binding);

    }

    //填充每个item的视图
    @Override
    public void onBindViewHolder(CharaListViewHolder holder, int position){
        holder.getBinding().setCharaViewModel(charaDetailsViewModels.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount(){
        return charaDetailsViewModels.size();
    }


    public void update(List<CharaDetailsViewModel> charaDetailsViewModels){
        this.charaDetailsViewModels = charaDetailsViewModels;
        notifyDataSetChanged();
    }

    //每一个item的视图控制者类（holder）
    public static class CharaListViewHolder extends RecyclerView.ViewHolder{

        private ListItemCharaBinding binding;
        CharaListViewHolder(ListItemCharaBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        ListItemCharaBinding getBinding(){
            return binding;
        }

    }

}
