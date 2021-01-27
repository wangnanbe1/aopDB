package com.trust.mediclarecordcollection.utils;


import org.springframework.stereotype.Component;




import java.util.UUID;

/**
 *  UUID工具类
 * @author wc
 */

@Component
public class UUIDUtils {
    /*public static void main(String[] args) {
        System.out.println(randomUUID().length());
        System.out.println(uuidVersion());
        System.out.println(randomUUIDWithoutRod());

    }*/
    /**
     * @return 一个随机的UUID
     */
    public  String randomUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    /**
     * UUID 一共5个版本，Java中的一般是第四个版本
     *
     * @return 获取UUID的版本号
     */
    public  int uuidVersion() {
        return randomUUID().charAt(14) - 48;
    }
    /**
     * @return 一个随机的没有横杠的UUID
     */
    public  String randomUUIDWithoutRod() {
        return randomUUID().replaceAll("-", "");
    }
    public  String upperCaseUUID() {
        return randomUUIDWithoutRod().toUpperCase();
    }
}