package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaBinding;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaDetailsBinding;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;
import com.github.malitsplus.shizurunotes.ui.SharedViewModelFactory;
import com.github.malitsplus.shizurunotes.ui.charalist.CharaListFragmentDirections;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsFragment extends Fragment implements View.OnClickListener {

    private SharedViewModel sharedViewModel;
    private CharaDetailsViewModel detailsViewModel;
    private FragmentCharaDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara_details, container, false);

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        SharedViewModelFactory factory = new SharedViewModelFactory(sharedViewModel);
        detailsViewModel = new ViewModelProvider(this, factory).get(CharaDetailsViewModel.class);

        binding.setDetailsViewModel(detailsViewModel);
        binding.setClickListener(this);

        //set title
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(sharedViewModel.getSelectedChara().unitName);

        //角色技能顺序
        RecyclerView recyclerView = binding.attackPatternRecycler;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 6);
        AttackPatternAdapter adapter = new AttackPatternAdapter(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        //技能
        RecyclerView recyclerViewSkill = binding.skillRecycler;
        LinearLayoutManager layoutManagerSkill = new LinearLayoutManager(getContext());
        SkillAdapter adapterSkill = new SkillAdapter(getContext());
        recyclerViewSkill.setLayoutManager(layoutManagerSkill);
        recyclerViewSkill.setAdapter(adapterSkill);
        recyclerViewSkill.setNestedScrollingEnabled(false);

        //设置观察者
        detailsViewModel.getMutableChara().observe(getViewLifecycleOwner(), (chara) -> {
            binding.setDetailsViewModel(detailsViewModel);
            adapter.update(chara.attackPatternList.get(0).items);
            adapterSkill.update(chara.skills);

            if(chara.attackPatternList.size() == 1){
                binding.attackPatternConstraint.removeView(binding.textAnotherMode);
                binding.attackPatternConstraint.removeView(binding.attackPatternRecycler2);
            } else {
                RecyclerView recyclerView2 = binding.attackPatternRecycler2;
                GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 6);
                AttackPatternAdapter adapter2 = new AttackPatternAdapter(getContext());
                recyclerView2.setLayoutManager(layoutManager2);
                recyclerView2.setAdapter(adapter2);
                recyclerView2.setNestedScrollingEnabled(false);
                adapter2.update(chara.attackPatternList.get(1).items);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.detailsItemChara){
            NavDirections action = CharaDetailsFragmentDirections.actionNavCharaDetailsToNavCharaProfile();
            Navigation.findNavController(v).navigate(action);
        }
    }

}
