package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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

import java.lang.reflect.InvocationTargetException;

public class CharaDetailsFragment extends Fragment {

    private CharaDetailsViewModel mViewModel;
    private String charaId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.charaId =CharaDetailsFragmentArgs.fromBundle(getArguments()).getCharaId();
        FragmentCharaDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara_details, container, false);
        //需要传参数，必须新建一个factory
        CharaDetailsViewModelFactory factory = new CharaDetailsViewModelFactory(charaId);
        mViewModel = ViewModelProviders.of(this, factory).get(CharaDetailsViewModel.class);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }



}
