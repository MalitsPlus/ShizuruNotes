package com.github.malitsplus.shizurunotes.common;

import android.os.Build;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Utils {
    /***
     * 单纯的复制文件
     * @param srcPath 源文件路径
     * @param desPath 目标路径
     * @param delete  是否删除源
     */
    public static void copyFile(String fileName, String srcPath, String desPath, boolean delete){
        String srcFilePath = srcPath + fileName;
        String desFilePath = desPath + fileName;
        File dataBaseDir = new File(desPath);
        //检查数据库文件夹是否存在
        if(!dataBaseDir.exists())
            dataBaseDir.mkdirs();

        File exDB = new File(desFilePath);
        if(exDB.exists())
            exDB.delete();
        try{
            FileInputStream fileInputStream = new FileInputStream(srcFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(exDB);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fileInputStream.read(buffer)) > 0)
                fileOutputStream.write(buffer, 0, count);
            fileOutputStream.flush();
            fileOutputStream.close();
            fileInputStream.close();
            if(delete){
                File srcFile = new File(srcFilePath);
                srcFile.delete();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void deleteFile(File file){
        try {
            file.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean checkFile(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    public static void checkFileAndDeleteIfExists(File file){
        if (file.exists())
            deleteFile(file);
    }

    /***
     * 以逗号分隔字符串
     * @param list
     * @return
     */
    public static String splitIntegerWithComma(ArrayList<Integer> list){
        if(list.isEmpty())
            return null;

        StringBuilder sb = new StringBuilder();
        for(Integer item : list){
            sb.append(item).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }


    public static Object getValueFromObject(@NonNull Object object, @NonNull String fieldName) {
        Field field;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }




    private static DecimalFormat format = new DecimalFormat("#");
    public static String roundDownDouble(double value){
        return format.format(Math.floor(value));
    }
    public static String roundUpDouble(double value){
        return format.format(Math.ceil(value));
    }
    public static String roundDouble(double value){
        return format.format(Math.round(value));
    }
}
