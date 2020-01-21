package com.github.malitsplus.shizurunotes.ui.charalist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.github.malitsplus.shizurunotes.common.Statics;
import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.ui.SharedViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharaListViewModel extends AndroidViewModel {

    private SharedViewModel sharedViewModel;
    public MutableLiveData<List<Chara>> liveCharaList = new MutableLiveData<>();

    public CharaListViewModel(Application application) {
        super(application);
        liveCharaList.setValue(sharedViewModel.getCharaList());
    }

    public void filter(String position, int type, SortValue sortValue, boolean desc){
        List<Chara> charaToShow = new ArrayList<>();
        for(Chara chara : liveCharaList.getValue()){
            if(checkPosition(chara, position) && checkType(chara, type))
                charaToShow.add(chara);
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
                    valueB = b.charaProperty.getMagicDef();
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
            return (desc ? 1 : -1) * Long.compare(valueB, valueA);
        });
        liveCharaList.setValue(charaToShow);
    }

    private boolean checkPosition(Chara chara, String position){
        return position.equals(Statics.FILTER_NULL) || position.equals(chara.position);
    }

    private boolean checkType(Chara chara, int type){
        return type == 0 || type == chara.atkType;
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