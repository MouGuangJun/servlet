package com.servlet.osf.annotation;

import com.servlet.osf.constant.OSFType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OSF字段属性标注
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OSFMetaField {

    String label() default "";

    /**
     * 针对对象和数组时，只会判断是否为null
     */
    boolean require() default false;

    OSFType type() default OSFType.STRING;

    /**
     * 只对{@link OSFType#STRING} {@link OSFType#NUMBER} 有效
     */
    Length length() default @Length();

    /**
     * 仅对{@link OSFType#OBJECT_ARRAY}有效
     */
    Class<?> actualType() default Object.class;// 当JSON类型为数组时，其中的真实类型

//    int deep() default 1; // 当JSON类型为数组时，数组的深度

    /**
     * 整数部分长度
     */
    @Retention(RetentionPolicy.RUNTIME)
    @interface Length {
        int value() default 0;// 整数部分 AliasFor("integer")

        int decimal() default 0;// 小数部分
    }
}
