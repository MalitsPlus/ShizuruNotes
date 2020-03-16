package com.github.malitsplus.shizurunotes.common;

import android.app.Application;

import java.util.List;

public final class UserData {

    public static UserData userData;
    public Application application;

    public static void with(Application application) {
        if (userData == null) {
            userData = new UserData(application);
        }
    }

    private UserData(Application application) {
        this.application = application;
    }

    public List<Integer> lastEquipmentIds;
    public static void setLastEquipmentIds(List<Integer> ids) {
        userData.lastEquipmentIds = ids;
        userData.saveData();
    }

    private void saveData() {
        String json = JsonUtils.getJsonFromBean(userData);
        Utils.checkFile("");
    }
}
