package com.github.malitsplus.shizurunotes.ui.charalist;

import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.ui.MainActivity;
import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.UpdateManager;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaBinding;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

public class CharaListFragment extends Fragment implements UpdateManager.IFragmentCallBack {

    private SharedViewModel sharedViewModel;
    private CharaListViewModel charaListViewModel;
    private CharaListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        ((MainActivity)context).updateManager.setIFragmentCallBack(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        //需要用到AndroidViewModelFactory，不能用默认的创建方式
        ViewModelProvider viewModelProvider = new ViewModelProvider(this,
                        ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()));
        charaListViewModel = viewModelProvider.get(CharaListViewModel.class);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        charaListViewModel.setSharedViewModel(sharedViewModel);

        FragmentCharaBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara, container, false);
        binding.setViewModel(charaListViewModel);
        binding.setLifecycleOwner(this);

        drawerLayout = binding.charaDrawer;
        recyclerView = binding.charaListRecycler;

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CharaListAdapter(this.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(200);

        //设置观察者
        charaListViewModel.getLiveCharaList().observe(this, (charaList) ->
            adapter.update(charaList)
        );



        setHasOptionsMenu(true);
        setButtonListener(binding);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.fragment_chara_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_filter) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }

        }
        return false;
    }

    private void setButtonListener(FragmentCharaBinding binding){
        binding.btnConfirm.setOnClickListener((v) ->{

            String position;
            switch (binding.chipGroupPosition.getCheckedChipId()){
                case R.id.chip_position_forward :
                    position = Statics.FILTER_FORWARD;
                    break;
                case R.id.chip_position_middle :
                    position = Statics.FILTER_MIDDLE;
                    break;
                case R.id.chip_position_rear :
                    position = Statics.FILTER_REAR;
                    break;
                default :
                    position = "";
                    break;
            }

            int type;
            switch (binding.chipGroupAtkType.getCheckedChipId()){
                case R.id.chip_type_physical :
                    type = 1;
                    break;
                case R.id.chip_type_magical :
                    type = 2;
                    break;
                default :
                    type = 0;
                    break;
            }

            CharaListViewModel.SortValue sortValue;
            switch (binding.chipGroupSort.getCheckedChipId()){
                case R.id.chip_sort_position :
                    sortValue = CharaListViewModel.SortValue.POSITION;
                    break;
                case R.id.chip_sort_physical_atk :
                    sortValue = CharaListViewModel.SortValue.ATK;
                    break;
                case R.id.chip_sort_magical_atk :
                    sortValue = CharaListViewModel.SortValue.MAGIC_ATK;
                    break;
                case R.id.chip_sort_physical_def :
                    sortValue = CharaListViewModel.SortValue.DEF;
                    break;
                case R.id.chip_sort_magical_def :
                    sortValue = CharaListViewModel.SortValue.MAGIC_DEF;
                    break;
                case R.id.chip_sort_age :
                    sortValue = CharaListViewModel.SortValue.AGE;
                    break;
                case R.id.chip_sort_height :
                    sortValue = CharaListViewModel.SortValue.HEIGHT;
                    break;
                case R.id.chip_sort_weight :
                    sortValue = CharaListViewModel.SortValue.WEIGHT;
                    break;
                case R.id.chip_sort_burst_size:
                    sortValue = CharaListViewModel.SortValue.BUST_SIZE;
                    break;
                default :
                    sortValue = CharaListViewModel.SortValue.NEW;
                    break;
            }

            boolean desc;
            if(binding.chipGroupSortWay.getCheckedChipId() == R.id.chip_asc)
                desc = false;
            else
                desc = true;
            charaListViewModel.filter(position, type, sortValue, desc);
        });
    }

    @Override
    public void dbUpdateFinished(){
        sharedViewModel.loadData();
    }

}