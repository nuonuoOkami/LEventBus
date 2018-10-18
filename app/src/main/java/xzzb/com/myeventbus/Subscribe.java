package xzzb.com.myeventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用于方法上
@Target(ElementType.METHOD)
//运行时期注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    //enmu类型
    ThreadMode threadMode() default ThreadMode.MAIN;
    //可以设置默认值
    boolean sticky() default false;
    //默认值设置为0
    int priority() default 0;

}
