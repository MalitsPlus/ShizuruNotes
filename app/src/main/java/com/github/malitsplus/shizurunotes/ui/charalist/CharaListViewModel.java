package com.github.malitsplus.shizurunotes.ui.charalist;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.malitsplus.shizurunotes.db.DBHelper;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.ui.charadetails.CharaDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CharaListViewModel extends AndroidViewModel {

    private MutableLiveData<List<CharaDetailsViewModel>> mutableCharaDetailsViewModels;

    public CharaListViewModel(Application application) {
        super(application);
        mutableCharaDetailsViewModels = new MutableLiveData<>();
        loadData("", null);
    }

    public void loadData(String condition, @Nullable String sortValue){
        List<CharaDetailsViewModel> listCharaDetailsViewModel = new ArrayList<>();
        Cursor cursor = DBHelper.get(getApplication()).getCharaBase(condition);
        if(cursor == null)
            return;
        while (cursor.moveToNext()){
            Chara chara = new Chara();

            chara.setBasic(
                    cursor.getInt(cursor.getColumnIndex("unit_id")),
                    cursor.getString(cursor.getColumnIndex("unit_name")),
                    cursor.getInt(cursor.getColumnIndex("prefab_id")),
                    cursor.getInt(cursor.getColumnIndex("search_area_width")),
                    cursor.getInt(cursor.getColumnIndex("atk_type")),
                    cursor.getString(cursor.getColumnIndex("actual_name")),
                    cursor.getString(cursor.getColumnIndex("age")),
                    cursor.getString(cursor.getColumnIndex("guild")),
                    cursor.getString(cursor.getColumnIndex("race")),
                    cursor.getString(cursor.getColumnIndex("height")),
                    cursor.getString(cursor.getColumnIndex("weight")),
                    cursor.getString(cursor.getColumnIndex("birth_month")),
                    cursor.getString(cursor.getColumnIndex("birth_day")),
                    cursor.getString(cursor.getColumnIndex("blood_type")),
                    cursor.getString(cursor.getColumnIndex("favorite")),
                    cursor.getString(cursor.getColumnIndex("voice")),
                    cursor.getString(cursor.getColumnIndex("catch_copy")),
                    sortValue == null ? "" : cursor.getString(cursor.getColumnIndex(sortValue))
            );
            listCharaDetailsViewModel.add(new CharaDetailsViewModel(chara));
        }
        cursor.close();
        mutableCharaDetailsViewModels.setValue(listCharaDetailsViewModel);
    }

    public LiveData<List<CharaDetailsViewModel>> getCharaDetailsViewModel(){
        return mutableCharaDetailsViewModels;
    }
}