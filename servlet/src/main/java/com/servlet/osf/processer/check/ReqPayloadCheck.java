package com.servlet.osf.processer.check;

import cn.hutool.core.util.ReflectUtil;
import com.servlet.osf.OSFContext;
import com.servlet.osf.entity.OSFMetaField;
import com.servlet.osf.message.OSFTips;
import com.servlet.osf.message.ReqServiceMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

import static com.servlet.osf.constant.OSFType.*;

/**
 * 检查请求信息是否满足规范
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqPayloadCheck implements Checker {
    @Getter
    private final OSFTips tips = new OSFTips();
    private ReqServiceMsg request;// 请求信息
    private OSFContext context;// 上下文

    /**
     * 执行检查的操作
     */
    public OSFTips check() {
        OSFMetaField metaField = context.getOsfService().getReqMetaField();
        check(request.getBody(), metaField);

        return tips;
    }

    /**
     * 检查单个对象是否满足规范
     *
     * @param obj       请求体
     * @param metaField 元数据
     */
    public void check(Object obj, OSFMetaField metaField) {
        if (metaField.getType() != ROOT) {
            if (metaField.isRequire() && obj == null) {
                tips.error("必须属性[" + metaField.getName() + "]未录入！");
            }
        }

        // 如果值为空，直接返回
        if (obj == null) return;
        // 检查每个子元素
        for (OSFMetaField child : metaField.getChildren()) {
            Object value = ReflectUtil.getFieldValue(obj, child.getName());
            // 基础类型
            if (child.isPrimitive()) {
                checkSingle(value, child);
            }
            // 对象类型
            if (child.getType() == OBJECT) {
                check(value, child);
            }
            // 对象数组
            if (child.getType() == OBJECT_ARRAY) {
                checkArray(value, child);
            }
        }
    }

    /**
     * 检查单个属性是否满足规范
     *
     * @param value 值
     * @param child 元素据
     */
    private void checkSingle(Object value, OSFMetaField child) {
        // 检查是否必须
        if (child.isRequire() && value == null) {
            tips.error("必须属性[" + child.getName() + "]未录入！");
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
                tips.error("属性：[" + child.getName() + "]内容超长！期望长度：[" + integer + "]，实际长度：[" + str.length() + "]！");
                return;
            }
        }
        // 数字
        if (child.getType() == NUMBER) {
            String number = new BigDecimal(str).abs().toPlainString();
            String[] numbers = number.split("\\.");
            if (numbers.length == 1) {
                if (numbers[0].length() > integer) {
                    tips.error("属性：[" + child.getName() + "]内容超长！整数部分期望长度：[" + integer + "]，实际长度：[" + numbers[0].length() + "]！");
                }
            } else if (numbers[0].length() > integer && numbers[1].length() > decimal) {
                tips.error("属性：[" + child.getName() + "]内容超长！整数部分：[" + numbers[0].length() + "]，小数部分：[" + numbers[1].length() + "]！");
            }
        }
    }

    /**
     * 检查数组是否满足规范
     *
     * @param array 数组
     * @param child 元数据
     */
    @SuppressWarnings("unchecked")
    private void checkArray(Object array, OSFMetaField child) {
        if (array instanceof Collection) {// 集合
            for (Object obj : (Collection<Object>) array) {
                // 如果子元素还是数组/集合，接着解析
                if (obj.getClass().isArray() || obj instanceof Collection) {
                    checkArray(obj, child);
                } else {
                    check(obj, child);
                }
            }
        }

        if (array instanceof Object[]) {// 数组
            for (Object obj : (Object[]) array) {
                // 如果子元素还是数组/集合，接着解析
                if (obj.getClass().isArray() || obj instanceof Collection) {
                    checkArray(obj, child);
                } else {
                    check(obj, child);
                }
            }
        }
    }
}
