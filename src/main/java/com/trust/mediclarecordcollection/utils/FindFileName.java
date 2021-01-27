package com.trust.mediclarecordcollection.utils;

import java.io.File;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 19:12
 * @Description:
 */
public class FindFileName {

    public static String findFileName(String filePath){
        String[] split = filePath.split("\\\\");
        String fileName = "";
        for(String str : split){
            if(str.contains(".pdf")){
                fileName = str;
            }
        }

        return fileName;
    }

    public static String findFileDir(String filePath,String fileName){
        return filePath.split(fileName)[0];
    }

    public static void main(String[] args) {
        String file = "\\2021\\0107\\2021010720000375.pdf";
        String fileName = findFileName(file);
        String fileDir = findFileDir(file, fileName);
        System.out.println(fileName);
        System.out.println("pdf"+ File.separator+fileDir);
    }




}
