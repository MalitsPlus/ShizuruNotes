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
import androidx.lifecycle.ViewModel;
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
import com.github.malitsplus.shizurunotes.ui.SharedViewModelFactory;

public class CharaListFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private CharaListViewModel charaListViewModel;
    private CharaListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        //sometimes will occur NPE?
        //((MainActivity)context).updateManager.setIFragmentCallBack(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        SharedViewModelFactory factory = new SharedViewModelFactory(sharedViewModel);
        charaListViewModel = new ViewModelProvider(this, factory).get(CharaListViewModel.class);

        FragmentCharaBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chara, container, false);
        binding.setViewModel(charaListViewModel);
        binding.setLifecycleOwner(this);

        //get controls
        drawerLayout = binding.charaDrawer;
        recyclerView = binding.charaListRecycler;

        //set layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //set adapter
        adapter = new CharaListAdapter(this.getContext(), sharedViewModel);
        recyclerView.setAdapter(adapter);

        //减少开销
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(200);

        //设置观察者
        charaListViewModel.getLiveCharaList().observe(getViewLifecycleOwner(), (charaList) ->
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
                    position = Statics.FILTER_NULL;
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

            boolean asc;
            if(binding.chipGroupSortWay.getCheckedChipId() == R.id.chip_asc)
                asc = true;
            else
                asc = false;
            charaListViewModel.filter(position, type, sortValue, asc);
        });
    }

    public void updateList(){
        charaListViewModel.filterDefault();
    }
}