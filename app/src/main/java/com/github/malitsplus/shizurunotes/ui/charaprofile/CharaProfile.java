package com.github.malitsplus.shizurunotes.ui.charaprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaDetailsBinding;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaProfileBinding;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;
import com.github.malitsplus.shizurunotes.ui.SharedViewModelFactory;
import com.github.malitsplus.shizurunotes.ui.charadetails.CharaDetailsViewModel;


public class CharaProfile extends Fragment {

    private SharedViewModel sharedViewModel;
    private CharaProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentCharaProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara_profile, container, false);

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        SharedViewModelFactory factory = new SharedViewModelFactory(sharedViewModel);
        profileViewModel = new ViewModelProvider(this, factory).get(CharaProfileViewModel.class);

        binding.setChara(sharedViewModel.getSelectedChara());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(sharedViewModel.getSelectedChara().actualName);

        return binding.getRoot();
    }
}
