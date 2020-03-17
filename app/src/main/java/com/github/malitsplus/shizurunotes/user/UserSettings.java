package com.github.malitsplus.shizurunotes.user;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.github.malitsplus.shizurunotes.utils.JsonUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Nullable;

public class UserSettings {

    private static final String userDataFileName = "userData.json";
    private static volatile UserSettings instance;
    private UserData userData;
    private Application application;

    private UserSettings(Application application){
        this.application = application;
        String json = getJson();
        if (!json.isEmpty()) {
            userData = JsonUtils.getBeanFromJson(json, UserData.class);
        } else {
            userData = new UserData();
        }
    }

    public static void with(Application application){
        if(instance == null) {
            synchronized (UserSettings.class) {
                instance = new UserSettings(application);
            }
        }
    }

    public static UserSettings get(){
        if(instance == null){
            throw new NullPointerException("No instance of UserSettings.");
        }
        return instance;
    }

    public SharedPreferences getPreference(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    public UserData getUserData() {
        return userData;
    }

    public void setLastEquipmentIds(List<Integer> ids) {
        userData.lastEquipmentIds = ids;
        saveJson();
    }

    @Nullable
    public List<Integer> getLastEquipmentIds() {
        return userData.lastEquipmentIds;
    }

    private void saveJson() {
        new Thread(() -> {
            String json = JsonUtils.getJsonFromBean(userData);
            try (FileOutputStream fos = application.openFileOutput(userDataFileName, Context.MODE_PRIVATE)) {
                fos.write(json.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String getJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = application.openFileInput(userDataFileName)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}