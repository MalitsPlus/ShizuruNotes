package com.github.malitsplus.shizurunotes.ui.charadetails;

import androidx.lifecycle.ViewModel;

public class CharaDetailsViewModel extends ViewModel {

    //region getters and setters

    public String getCharaId() {
        return charaId;
    }

    public void setCharaId(String charaId) {
        this.charaId = charaId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//endregion

    private String charaId;
    private String imageUrl = "https://redive.estertion.win/card/full/112031.webp";

    public CharaDetailsViewModel(String charaId){
        this.charaId = charaId;
    }


}
