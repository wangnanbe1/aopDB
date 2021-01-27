package com.trust.mediclarecordcollection.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 19:20
 * @Description:
 */
public class MakeDirUtils {

    private static final Logger logger = LoggerFactory.getLogger(MakeDirUtils.class);

    /**
     * uploadFilePath: c://upload
     */
    private final static String dirPath ="c://upload";


    public static String mkdirs(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDate = now.format(formatter);
        File file = new File(dirPath+File.separator+nowDate);
        file.mkdirs();
        logger.debug("dir path {}",file.getPath());
        return file.getPath();
    }

    /**
     *
     * @return
     */
    public static boolean deleDir(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.plusDays(-3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = localDateTime.format(formatter);
        File file = new File(dirPath+File.separator+format);
        logger.debug("dir path : {}",file.getPath());
        return file.delete();
    }

    public static void main(String[] args) {
//        mkdirs();
//        MakeDirUtils makeDirUtils = new MakeDirUtils();
        deleDir();
    }

}
