package com.laoxu.idjiami.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: YLBG-LDH-1506
 * @Description: 标识id需要加密使用IdHandler，写model上
 * @Date: 2018/05/15
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IdEncryption {
}
