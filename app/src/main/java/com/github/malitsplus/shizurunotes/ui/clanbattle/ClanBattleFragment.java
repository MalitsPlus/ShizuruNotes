package com.github.malitsplus.shizurunotes.ui.clanbattle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.malitsplus.shizurunotes.R;

public class ClanBattleFragment extends Fragment {

    private ClanBattleViewModel clanBattleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clanBattleViewModel =
                ViewModelProviders.of(this).get(ClanBattleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clan_battle, container, false);



        /*
        final TextView textView = root.findViewById(R.id.text_gallery);
        clanBattleViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */
        return root;
    }
}