package com.servlet.osf.loader;

import cn.hutool.core.util.ReflectUtil;
import com.servlet.osf.constant.OSFType;
import com.servlet.osf.entity.OSFMetaField;
import com.servlet.osf.entity.OSFService;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.servlet.osf.constant.OSFType.*;

/**
 * 解析请求/响应对象的元数据
 */
@Data
public class OSFMetaFieldLoader implements OSFLoader {
    private OSFService OSFService;

    @Override
    public void load() {
        if (OSFService == null) return;
        Object requestObj = OSFService.getService().getRequestObj();
        Object responseObj = OSFService.getService().getResponseObj();
        OSFService.setReqMetaField(parseMetaField(requestObj));
        OSFService.setRespMetaField(parseMetaField(responseObj));
    }

    // 解析属性
    private OSFMetaField parseMetaField(Object obj) {
        OSFMetaField metaField = new OSFMetaField();
        metaField.setName("root");
        metaField.setLabel("根节点");
        metaField.setType(ROOT);
        parseField(obj.getClass(), metaField);// 开始解析

        return metaField;
    }

    /**
     * 解析出属性的配置信息
     *
     * @param clazz     被解析对象的字节码
     * @param metaField 解析完的元素据
     */
    private void parseField(Class<?> clazz, OSFMetaField metaField) {
        // 设置其子元素
        ArrayList<OSFMetaField> children = new ArrayList<>();
        metaField.setChildren(children);
        for (Field field : ReflectUtil.getFields(clazz)) {
            com.servlet.osf.annotation.OSFMetaField metaFieldAnno;
            // 查询注解
            if ((metaFieldAnno = field.getAnnotation(com.servlet.osf.annotation.OSFMetaField.class)) != null) {
                OSFMetaField subMetaField = new OSFMetaField();
                OSFType type = metaFieldAnno.type();// 数据类型
                Class<?> fieldType = field.getType();// java属性类型
                // 基础类型
                packMetaField(field.getName(), subMetaField, metaFieldAnno);
                // 对象类型
                if (type == OBJECT) {
                    parseField(fieldType, subMetaField);
                }
                // 对象类型的数组
                if (type == OBJECT_ARRAY) {
                    if (metaField.getActualType() != Object.class) {
                        parseField(metaFieldAnno.actualType(), subMetaField);
                    }
                }

                children.add(subMetaField);
            }
        }
    }

    /**
     * 封装基本类型的元数据
     *
     * @param name          数据名
     * @param osfMetaField  封装结果
     * @param metaFieldAnno 元数据注解
     */
    private void packMetaField(String name, OSFMetaField osfMetaField, com.servlet.osf.annotation.OSFMetaField metaFieldAnno) {
        osfMetaField.setName(name);
        osfMetaField.setLabel(metaFieldAnno.label());
        osfMetaField.setRequire(metaFieldAnno.require());
        osfMetaField.setType(metaFieldAnno.type());
        // 设置数据的长度
        com.servlet.osf.annotation.OSFMetaField.Length length = metaFieldAnno.length();
        OSFMetaField.Length osfLength = new OSFMetaField.Length();
        osfLength.setInteger(length.value());
        osfLength.setDecimal(length.decimal());
        osfMetaField.setLength(osfLength);
    }
}
