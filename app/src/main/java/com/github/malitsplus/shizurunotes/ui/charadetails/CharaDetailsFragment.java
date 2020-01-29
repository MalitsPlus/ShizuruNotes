package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaBinding;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaDetailsBinding;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;
import com.github.malitsplus.shizurunotes.ui.SharedViewModelFactory;

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private CharaDetailsViewModel detailsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentCharaDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara_details, container, false);

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        SharedViewModelFactory factory = new SharedViewModelFactory(sharedViewModel);
        detailsViewModel = new ViewModelProvider(this, factory).get(CharaDetailsViewModel.class);

        binding.setDetailsViewModel(detailsViewModel);

        //设置观察者
        detailsViewModel.getMutableChara().observe(getViewLifecycleOwner(), (chara) -> {
            binding.setDetailsViewModel(detailsViewModel);
        });

        return binding.getRoot();
    }

}
