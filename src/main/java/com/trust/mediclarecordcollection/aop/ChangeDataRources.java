package com.trust.mediclarecordcollection.aop;

import com.trust.mediclarecordcollection.annotation.DataSource;
import com.trust.mediclarecordcollection.config.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;


/**
 * @auther wangnanbei
 * @date 2020/7/24 17:31
 */


@Component
@Aspect
public class ChangeDataRources {


    @Pointcut("@annotation(com.trust.mediclarecordcollection.annotation.DataSource)")
    public void permissionCheck() {

    }

    /**
     * 在连接点执行之前执行的通知
     */
    @Before("permissionCheck()")
    public void beforeSwitchDS(JoinPoint point){
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();

        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DataSource注解
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource annotation = method.getAnnotation(DataSource.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);
    }


    @After("permissionCheck()")
    public void afterSwitchDS(JoinPoint point){
        DataSourceContextHolder.clearDB();
    }

}
