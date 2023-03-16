package com.servlet.osf;

import com.servlet.osf.entity.OSFService;
import com.servlet.osf.message.OSFTips;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.server.ServerModel;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OSFContext {
    private static final ThreadLocal<OSFContext> globalContext = new InheritableThreadLocal<>();
    private String reqJsonStr;// 请求的json内容
    private ReqServiceMsg request;// 请求的json信息
    private RespServiceMsg response;// 响应json信息
    private ServerModel serverModel;// 服务处理器
    private HttpServletRequest req;// 请求信息
    private HttpServletResponse resp;// 响应信息
    private String clientAddress;// 客户端地址
    private String serverAddress;// 服务器地址
    private Charset encoding;// 编码
    private OSFService osfService;// 服务码
    private List<OSFTips> messages;// 错误提示信息

    private final Map<String, Object> attributes = new HashMap<>();

    public static void setContext(OSFContext context) {
        globalContext.set(context);
    }

    public static OSFContext getContext() {
        return globalContext.get();
    }

    public static void removeContext() {
        globalContext.remove();
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
