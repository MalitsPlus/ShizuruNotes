package com.github.malitsplus.shizurunotes.utils;

import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BrotliUtils {
    /**缓冲字节*/
    public static final int BUFFER = 1024;
    /**后缀名*/
    public static final String EXT = ".br";

    /**
     * 数据解压缩
     * @param data 压缩的数据
     * @return
     * @throws IOException
     */
    public static byte[] deCompress(byte[] data) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 解压缩
        deCompress(bais, baos);

        data = baos.toByteArray();

        baos.flush();
        baos.close();
        bais.close();

        return data;
    }

    /**
     * 文件解压缩
     * @param file 压缩文件
     * @param delete 是否删除源文件
     * @throws IOException
     */
    public static void deCompress(File file, boolean delete) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""));

        deCompress(fis, fos);

        fos.flush();
        fos.close();
        fis.close();

        if (delete) {
            file.delete();
        }
    }

    /**
     * 解压缩
     * @param is 输入流
     * @param os 输出流
     * @throws IOException
     */
    private static void deCompress(InputStream is, OutputStream os) throws IOException {
        BrotliCompressorInputStream bcis = new BrotliCompressorInputStream(is);

        int count;
        byte data[] = new byte[BUFFER];

        while((count = bcis.read(data, 0, BUFFER)) != -1){
            os.write(data, 0, count);
        }

        bcis.close();
    }

    /**
     * 文件解压缩
     * @param path 文件路径
     * @param delete 是否删除源文件
     * @throws IOException
     */
    public static void deCompress(String path, boolean delete) throws IOException{
        File file = new File(path);
        deCompress(file, delete);
    }
}
