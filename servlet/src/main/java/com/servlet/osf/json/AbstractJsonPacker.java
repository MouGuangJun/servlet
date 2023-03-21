package com.servlet.osf.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson的公共配置
 */
public abstract class AbstractJsonPacker {
    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        //屏蔽get方法的序列化
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        //设置任何属性可见
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // 生成 module
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(new PageInfoSerializer());
//        module.addDeserializer(PageInfo.class, new PageInfoDeserializer());
        // 注册 module
//        mapper.registerModule(module);
    }
}
