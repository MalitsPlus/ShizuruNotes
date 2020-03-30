package com.github.malitsplus.shizurunotes.utils;

import com.github.malitsplus.shizurunotes.common.Statics;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static String getDbDirectoryPath() {
        return Utils.getApp().getDataDir().getAbsolutePath() + "/databases";
    }

    public static String getDbFilePath() {
        return Utils.getApp().getDatabasePath(Statics.DB_FILE_NAME).getAbsolutePath();
    }

    public static String getCompressedDbFilePath() {
        return Utils.getApp().getDatabasePath(Statics.DB_FILE_NAME_COMPRESSED).getAbsolutePath();
    }

    public static String getFileFilePath(String fileName) {
        return Utils.getApp().getFilesDir().getAbsolutePath() + "/" + fileName;
    }

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

    public static boolean deleteDirectory(File directoryFile) {
        if (directoryFile.isDirectory()) {
            File[] files = directoryFile.listFiles();
            if (files != null) {
                for (File child : files) {
                    deleteDirectory(child);
                }
            }
        }
        return deleteFile(directoryFile);
    }

    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    public static boolean deleteFile(File file) {
        boolean flag = true;
        try {
            if (!file.delete()) {
                flag = false;
                throw new IOException("Failed to delete file: " + file.getAbsolutePath() + ". Size: " + file.length() / 1024 + "KB.");
            } else {
                LogUtils.file("FileDelete", "Delete file " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            LogUtils.file(LogUtils.E, "FileDelete", e.getMessage());
        }
        return flag;
    }

    public static boolean checkFile(@NotNull File file){
        if (!file.exists()) {
            LogUtils.file(LogUtils.I, "FileCheck", "FileNotExists: " + file.getAbsolutePath());
            return false;
        }
        return true;
    }

    public static boolean checkFile(String filePath){
        File file = new File(filePath);
        return checkFile(file);
    }

    public static boolean checkFileAndSize(String filePath, long border) {
        File file = new File(filePath);
        if (!checkFile(file)) {
            return false;
        }
        if (file.length() < border * 1024) {
            LogUtils.file(LogUtils.I, "FileCheck", "AbnormalDbFileSize: " + file.length() / 1024 + "KB." + " At: " + file.getAbsolutePath());
            return false;
        }
        LogUtils.file(LogUtils.I, "FileCheck", file.getAbsolutePath() + ". Size: " + file.length() / 1024 + "KB.");
        return true;
    }

    public static void checkFileAndDeleteIfExists(File file){
        if (file.exists()) deleteFile(file);
    }
}
