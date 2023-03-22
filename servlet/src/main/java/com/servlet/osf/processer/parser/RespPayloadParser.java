package com.servlet.osf.processer.parser;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.servlet.osf.OSFContext;
import com.servlet.osf.entity.OSFMetaField;
import com.servlet.osf.utils.OSFUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import static com.servlet.osf.constant.OSFType.*;

/**
 * 解析响应信息
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPayloadParser implements OSFParser {
    private Object respPayload;
    private OSFContext context;

    @Override
    public void parse() {
        if (respPayload == null || context == null) {
            log.warn("响应体或者OSF上下文为空，不进行解析操作！");
            return;
        }

        // 响应元数据
        OSFMetaField metaField = context.getOsfService().getRespMetaField();
        parse(respPayload, metaField);
    }

    private void parse(Object obj, OSFMetaField metaField) {
        if (metaField.getType() != ROOT) {
            if (metaField.isRequire() && obj == null) {
                log.warn("必须属性[" + metaField.getName() + "]未录入！");
            }
        }

        // 如果值为空，直接返回
        if (obj == null) return;
        // 检查每个子元素
        for (OSFMetaField child : metaField.getChildren()) {
            // 基础类型
            if (child.isPrimitive()) {
                parseSingle(obj, child);
            }
            // 获取对象值
            Object value = OSFUtils.getFieldValue(obj, child.getName());
            // 对象类型
            if (child.getType() == OBJECT) {
                parse(value, child);
            }
            // 对象数组
            if (child.getType() == OBJECT_ARRAY) {
                parseArray(value, child);
            }
        }
    }

    /**
     * 解析单个属性
     *
     * @param obj   对象
     * @param child 元素据
     */
    private void parseSingle(Object obj, OSFMetaField child) {
        // 获取对象值
        Object value = OSFUtils.getFieldValue(obj, child.getName());
        // 检查是否必须
        if (child.isRequire() && value == null) {
            log.warn("必须属性[" + child.getName() + "]未录入！");
            return;
        }

        // 值为空，不再进行检查
        if (value == null) return;

        // 检查属性长度（只检查String和Number两种类型）
        OSFMetaField.Length length = child.getLength();
        int integer = length.getInteger();
        int decimal = length.getDecimal();
        if (integer == 0 && decimal == 0) return;
        // 字符串
        String str = value.toString();
        if (child.getType() == STRING) {
            if (str.length() > integer) {
                log.warn("属性：[" + child.getName() + "]内容超长！期望长度：[" + integer + "]，实际长度：[" + str.length() + "]！");
                return;
            }
        }
        // 数字
        if (child.getType() == NUMBER) {
            BigDecimal bd = new BigDecimal(str);
            String number = bd.abs().toPlainString();
            String[] numbers = number.split("\\.");
            if (numbers[0].length() > integer) {
                log.warn("属性：[" + child.getName() + "]内容超长！整数部分期望长度：[" + integer + "]，实际长度：[" + numbers[0].length() + "]！");
            }

            // 小数位超长，四舍五入
            if (numbers.length == 2 && numbers[1].length() > decimal) {
                String name = child.getName();// 属性名称
                bd = bd.setScale(decimal, RoundingMode.HALF_UP);
                setDecimalValue(obj, name, bd);
            }
        }
    }

    /**
     * 设置小数数据的对象值
     *
     * @param obj  对象
     * @param name 属性名称
     * @param bd   bigdeciaml的小数数据
     */
    private void setDecimalValue(Object obj, String name, BigDecimal bd) {
        Field field = ReflectUtil.getField(obj.getClass(), name);
        Class<?> fieldType = field.getType();
        if (ClassUtil.isAssignable(Double.class, fieldType)) {// double
            ReflectUtil.setFieldValue(obj, field, bd.doubleValue());
        } else if (ClassUtil.isAssignable(Float.class, fieldType)) {// float
            ReflectUtil.setFieldValue(obj, field, bd.floatValue());
        } else if (ClassUtil.isAssignable(BigDecimal.class, fieldType)) {// bigdecimal
            ReflectUtil.setFieldValue(obj, field, bd);
        }
    }

    /**
     * 解析数组
     *
     * @param array 数组
     * @param child 元数据
     */
    @SuppressWarnings("unchecked")
    private void parseArray(Object array, OSFMetaField child) {
        if (array instanceof Collection) {// 集合
            for (Object obj : (Collection<Object>) array) {
                // 如果子元素还是数组/集合，接着解析
                if (obj.getClass().isArray() || obj instanceof Collection) {
                    parseArray(obj, child);
                } else {
                    parse(obj, child);
                }
            }
        }

        if (array instanceof Object[]) {// 数组
            for (Object obj : (Object[]) array) {
                // 如果子元素还是数组/集合，接着解析
                if (obj.getClass().isArray() || obj instanceof Collection) {
                    parseArray(obj, child);
                } else {
                    parse(obj, child);
                }
            }
        }
    }
}
