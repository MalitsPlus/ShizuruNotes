package com.github.nyanfantasia.shizurunotes.utils;

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
    /** Buffer byte */
    public static final int BUFFER = 1024;
    /** Postfix name */
    public static final String EXT = ".br";

    /**
     * deCompress data
     * @param data Compressed data
     * @return  deCompressed data
     * @throws IOException Exception
     */
    public static byte[] deCompress(byte[] data) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // deCompress
        deCompress(bais, baos);

        data = baos.toByteArray();

        baos.flush();
        baos.close();
        bais.close();

        return data;
    }

    /**
     * File deCompress
     * @param file Compressed file
     * @param delete Delete source file
     * @throws IOException Exception
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
     * deCompress
     * @param is Input Stream
     * @param os Output Stream
     * @throws IOException Exception
     */
    private static void deCompress(InputStream is, OutputStream os) throws IOException {
        BrotliCompressorInputStream bcis = new BrotliCompressorInputStream(is);

        int count;
        byte[] data = new byte[BUFFER];

        while((count = bcis.read(data, 0, BUFFER)) != -1){
            os.write(data, 0, count);
        }

        bcis.close();
    }

    /**
     * File deCompress
     * @param path File path
     * @param delete Delete source file
     * @throws IOException Exception
     */
    public static void deCompress(String path, boolean delete) throws IOException{
        File file = new File(path);
        deCompress(file, delete);
    }
}
