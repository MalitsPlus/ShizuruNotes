package com.github.malitsplus.shizurunotes.ui.charalist;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.malitsplus.shizurunotes.db.DBHelper;
import com.github.malitsplus.shizurunotes.data.Chara;

import java.util.ArrayList;
import java.util.List;

public class CharaListViewModel extends AndroidViewModel {

    //private MutableLiveData<String> mText;
    //private List<Chara> charaList;
    //private DBHelper dbHelper;

    private MutableLiveData<List<Chara>> mutableCharaModelList;

    public CharaListViewModel(Application application) {
        super(application);
        //mText = new MutableLiveData<>();
        //mText.setValue("This is home fragment");
        mutableCharaModelList = new MutableLiveData<>();
        loadData("", null);
    }



    public void loadData(String condition, @Nullable String sortValue){

        List<Chara> charaList = new ArrayList<>();
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
                    cursor.getString(cursor.getColumnIndex("height")),
                    cursor.getString(cursor.getColumnIndex("weight")),
                    sortValue == null ? "" : cursor.getString(cursor.getColumnIndex(sortValue))
            );

            charaList.add(chara);
        }
        cursor.close();
        mutableCharaModelList.setValue(charaList);
    }

    public LiveData<List<Chara>> getCharaList(){
        return mutableCharaModelList;
    }

}