package com.github.malitsplus.shizurunotes.ui.charalist;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharaListViewModel extends ViewModel {

    private SharedViewModel sharedViewModel;
    public MutableLiveData<List<Chara>> liveCharaList = new MutableLiveData<>();

    public CharaListViewModel(SharedViewModel sharedViewModel){
        this.sharedViewModel = sharedViewModel;
        filterDefault();
    }

    public void filter(@NonNull String position,
                       int type,
                       @NonNull SortValue sortValue,
                       boolean asc){

        List<Chara> charaToShow = new ArrayList<>();
        for(Chara chara : sharedViewModel.getCharaList()){
            if(checkPosition(chara, position) && checkType(chara, type)) {
                setSortValue(chara, sortValue);
                charaToShow.add(chara);
            }
        }

        Collections.sort(charaToShow, (a, b) -> {
            long valueA;
            long valueB;
            switch (sortValue){
                case NEW:
                    valueA = a.startTime;
                    valueB = b.startTime;
                    break;
                case POSITION:
                    valueA = a.searchAreaWidth;
                    valueB = b.searchAreaWidth;
                    break;
                case ATK:
                    valueA = a.charaProperty.getAtk();
                    valueB = b.charaProperty.getAtk();
                    break;
                case MAGIC_ATK:
                    valueA = a.charaProperty.getMagicStr();
                    valueB = b.charaProperty.getMagicStr();
                    break;
                case DEF:
                    valueA = a.charaProperty.getDef();
                    valueB = b.charaProperty.getDef();
                    break;
                case MAGIC_DEF:
                    valueA = a.charaProperty.getMagicDef();
                    valueB = b.charaProperty.getMagicDef();
                    break;
                case AGE:
                    valueA = a.age.contains("?") ? 9999 : Long.parseLong(a.age);
                    valueB = b.age.contains("?") ? 9999 : Long.parseLong(b.age);
                    break;
                case HEIGHT:
                    valueA = a.height.contains("?") ? 9999 : Long.parseLong(a.height);
                    valueB = b.height.contains("?") ? 9999 : Long.parseLong(b.height);
                    break;
                case WEIGHT:
                    valueA = a.weight.contains("?") ? 9999 : Long.parseLong(a.weight);
                    valueB = b.weight.contains("?") ? 9999 : Long.parseLong(b.weight);
                    break;
                default:
                    valueA = a.unitId;
                    valueB = b.unitId;
                    break;
            }
            return (asc ? -1 : 1) * Long.compare(valueB, valueA);
        });
        liveCharaList.setValue(charaToShow);
    }

    public void filterDefault(){
        filter(Statics.FILTER_NULL, 0, SortValue.NEW, false);
    }

    private boolean checkPosition(Chara chara, String position){
        return position.equals(Statics.FILTER_NULL) || position.equals(chara.position);
    }
    private boolean checkType(Chara chara, int type){
        return type == 0 || type == chara.atkType;
    }
    private void setSortValue(Chara chara, SortValue sortValue){
        switch (sortValue){
            case POSITION:
                chara.sortValue = String.valueOf(chara.searchAreaWidth);
                break;
            case ATK:
                chara.sortValue = String.valueOf(chara.charaProperty.getAtk());
                break;
            case MAGIC_ATK:
                chara.sortValue = String.valueOf(chara.charaProperty.getMagicStr());
                break;
            case DEF:
                chara.sortValue = String.valueOf(chara.charaProperty.getDef());
                break;
            case MAGIC_DEF:
                chara.sortValue = String.valueOf(chara.charaProperty.getMagicDef());
                break;
            case AGE:
                chara.sortValue = chara.age;
                break;
            case HEIGHT:
                chara.sortValue = chara.height;
                break;
            case WEIGHT:
                chara.sortValue = chara.weight;
                break;
            default:
                chara.sortValue = "";
                break;
        }
    }

    public MutableLiveData<List<Chara>> getLiveCharaList(){
        return liveCharaList;
    }
    public void setSharedViewModel(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }

    public enum SortValue{
        NEW, POSITION, ATK, MAGIC_ATK, DEF, MAGIC_DEF, AGE, HEIGHT, WEIGHT, BUST_SIZE
    }
}