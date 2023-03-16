package com.servlet.osf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OSFMetaField {

    String label() default "";

    boolean require() default false;

    Length length() default @OSFMetaField.Length(integer = -1, decimal = -1);

//    @Target({})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Length {
        int integer();// 整数部分

        int decimal();// 小数部分
    }
}
