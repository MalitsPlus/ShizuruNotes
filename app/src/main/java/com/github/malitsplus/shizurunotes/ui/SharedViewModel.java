package com.github.malitsplus.shizurunotes.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.malitsplus.shizurunotes.data.Chara;
import com.github.malitsplus.shizurunotes.data.Property;
import com.github.malitsplus.shizurunotes.data.Skill;
import com.github.malitsplus.shizurunotes.db.DBHelper;
import com.github.malitsplus.shizurunotes.db.RawCharaStoryStatus;
import com.github.malitsplus.shizurunotes.db.RawUniqueEquipmentData;
import com.github.malitsplus.shizurunotes.db.RawUnitBasic;


import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private List<Chara> charaList = new ArrayList<>();;
    private Chara selectedChara;

    public SharedViewModel(){
        loadData();
    }

    /***
     * 从数据库读取所有角色数据。
     * 注意：此方法应该且仅应该在程序初始化时或数据库更新完成后使用。
     */
    public void loadData(){
        loadBasic();
        for(Chara chara : charaList){
            setCharaMaxData(chara);
            setCharaRarity(chara);
            setCharaStoryStatus(chara);
            setCharaPromotionStatus(chara);
            setCharaEquipments(chara);
            setUniqueEquipment(chara);

            setUnitSkillData(chara);

            chara.setCharaProperty();
        }
    }

    private void loadBasic(){
        List<RawUnitBasic> rawList = DBHelper.get().getCharaBase();
        if(rawList == null)
            return;
        for(RawUnitBasic rawUnitBasic : rawList){
            Chara chara = new Chara();
            rawUnitBasic.setCharaBasic(chara);
            charaList.add(chara);
        }
    }
    private void setCharaMaxData(Chara chara){
        chara.maxCharaLevel = DBHelper.get().getMaxCharaLevel() - 1;
        chara.maxCharaRank = DBHelper.get().getMaxCharaRank();
        chara.maxUniqueEquipmentLevel = DBHelper.get().getMaxUniqueEquipmentLevel();
    }
    private void setCharaRarity(Chara chara){
        DBHelper.get().getCharaRarity(chara.unitId).setCharaRarity(chara);
    }
    private void setCharaStoryStatus(Chara chara){
        List<RawCharaStoryStatus> rawList = DBHelper.get().getCharaStoryStatus(chara.charaId);
        Property storyStatus = new Property();

        for(RawCharaStoryStatus raw : rawList){
            storyStatus.plusEqual(raw.getCharaStoryStatus(chara));
        }
        chara.storyProperty = storyStatus;
    }
    private void setCharaPromotionStatus(Chara chara){
        DBHelper.get().getCharaPromotionStatus(chara.unitId).setPromotionStatus(chara);
    }
    private void setCharaEquipments(Chara chara){
        DBHelper.get().getCharaPromotion(chara.unitId).setCharaEquipments(chara);
    }
    private void setUniqueEquipment(Chara chara){
        RawUniqueEquipmentData uniqueEquipment = DBHelper.get().getUniqueEquipment(chara.unitId);
        if(uniqueEquipment != null){
            uniqueEquipment.setCharaUniqueEquipment(chara);
        }
    }

    private void setUnitSkillData(Chara chara){
        DBHelper.get().getUnitSkillData(chara.unitId).setCharaSkillMap(chara);


        chara.skillMap.forEach((key, skill) -> {
            //填充Skill中只有actionId和dependActionId（可能为0）的actionList
            DBHelper.get().getSkillData(skill.skillId).setSkillData(skill);


            for (Skill.Action action : skill.actions){
                //向actionList中填入其他具体值
                DBHelper.get().getSkillAction(action.actionId).setActionData(action);
            }


            for (Skill.Action action : skill.actions){

                if(action.dependActionId != 0){

                    for(Skill.Action searched : skill.actions){
                        if(searched.actionId == action.dependActionId){
                            //需要先建立params
                            searched.buildParameter();
                            action.dependAction = searched;
                            break;
                        }
                    }

                }

                action.buildParameter();
            }
        });



    }





    public Chara getSelectedChara() {
        return selectedChara;
    }
    public void setSelectedChara(Chara selectedChara) {
        this.selectedChara = selectedChara;
    }
    public List<Chara> getCharaList() {
        return charaList;
    }
}
