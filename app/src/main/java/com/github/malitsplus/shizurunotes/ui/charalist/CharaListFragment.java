package com.github.malitsplus.shizurunotes.ui.charalist;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.malitsplus.shizurunotes.ui.MainActivity;
import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.UpdateManager;
import com.github.malitsplus.shizurunotes.databinding.FragmentCharaBinding;

public class CharaListFragment extends Fragment implements UpdateManager.IFragmentCallBack {

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
        charaListViewModel.getCharaDetailsViewModel().observe(this, (charaDetailsViewModels) ->
            adapter.update(charaDetailsViewModels)
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
            String condition = "";
            String sortValue = null;
            switch (binding.chipGroupPosition.getCheckedChipId()){
                case R.id.chip_position_forward :
                    condition += " AND search_area_width < 300 ";
                    break;
                case R.id.chip_position_middle :
                    condition += " AND search_area_width BETWEEN 300 AND 600 ";
                    break;
                case R.id.chip_position_rear :
                    condition += " AND search_area_width BETWEEN 600 AND 900 ";
                    break;
                default :
                    break;
            }

            switch (binding.chipGroupAtkType.getCheckedChipId()){
                case R.id.chip_type_physical :
                    condition += " AND atk_type = 1 ";
                    break;
                case R.id.chip_type_magical :
                    condition += " AND atk_type = 2 ";
                    break;
                default :
                    break;
            }

            switch (binding.chipGroupSort.getCheckedChipId()){
                case R.id.chip_sort_position :
                    condition += " ORDER BY search_area_width ";
                    sortValue = "search_area_width";
                    break;
                case R.id.chip_sort_age :
                    condition += " ORDER BY CAST(up.age AS int) ";
                    sortValue = "age";
                    break;
                case R.id.chip_sort_height :
                    condition += " ORDER BY CAST(up.height AS int) ";
                    sortValue = "height";
                    break;
                case R.id.chip_sort_weight :
                    condition += " ORDER BY CAST(up.weight AS int) ";
                    sortValue = "weight";
                    break;
                default :
                    condition += " ORDER BY start_time ";
                    break;
            }

            switch (binding.chipGroupSortWay.getCheckedChipId()){
                case R.id.chip_asc :
                    condition += " ASC ";
                    break;
                default :
                    condition += " DESC ";
                    break;
            }

            charaListViewModel.loadData(condition, sortValue);
        });
    }

    @Override
    public void dbUpdateFinished(){
        charaListViewModel.loadData(null, null);
    }

}