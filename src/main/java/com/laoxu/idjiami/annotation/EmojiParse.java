package com.laoxu.idjiami.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@Inherited
@Documented
public @interface EmojiParse {

    //用来指定类型1代表存入数据库 2代表从数据库中取值
    int type() default 1;

}
