package com.servlet.osf;

import com.servlet.osf.client.OSFClient;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * OSF客户端上下文
 */
@Data
public class OSFClientContext implements Serializable {
    private static final long serialVersionUID = 513950755080802328L;
    private final Map<String, Object> attributes = new HashMap<>();// 属性
    private OSFClient client;// 客户端
    private String innerRequestId = UUID.randomUUID().toString();// 内部编号
    private String reqStr;// 请求报文字符串
    private Class<?> respClazz;// 响应对象字节码
    private static final ThreadLocal<OSFClientContext> globalContext = new InheritableThreadLocal<>();

    public static void setContext(OSFClientContext context) {
        globalContext.set(context);
    }

    public static OSFClientContext getContext() {
        return globalContext.get();
    }

    public static void removeContext() {
        globalContext.remove();
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
}
