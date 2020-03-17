package com.github.malitsplus.shizurunotes.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
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
        if (file.exists()) deleteFile(file);
    }

    public static void saveToInternalFile(String path, String file) {

    }
}
