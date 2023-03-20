package com.servlet.osf.entity;

import com.servlet.osf.constant.OSFType;
import lombok.Data;

import java.util.List;

import static com.servlet.osf.constant.OSFType.*;

/**
 * OSF字段属性
 */
@Data
public class OSFMetaField {
    private String name;// 名称
    private String label;// 描述
    private boolean require;// 是否必须
    private OSFType type;// 数据类型
    private Length length;// 数据长度
    private List<OSFMetaField> children;// 子元素
    private Class<?> actualType;// 数组真实类型
//    private int deep;// 数组的深度


    @Data
    public static class Length {
        private int integer;// 整数部分
        private int decimal;// 小数部分
    }

    public boolean isArray() {
        return type == PRIMITIVE_ARRAY || type == OBJECT_ARRAY || type == MAP_ARRAY;
    }

    public boolean isObject() {
        return type == OBJECT || type == MAP;
    }

    public boolean isPrimitive() {
        return type == STRING || type == NUMBER || type == BOOLEAN;
    }
}
