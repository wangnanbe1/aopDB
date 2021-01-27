package com.trust.mediclarecordcollection.annotation;

import java.lang.annotation.*;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/14 14:22
 * @Description:
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "pacsDB";
}
