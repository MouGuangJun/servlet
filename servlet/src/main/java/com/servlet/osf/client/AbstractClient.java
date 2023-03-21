package com.servlet.osf.client;

import cn.hutool.core.util.StrUtil;
import com.servlet.osf.OSFClientContext;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.json.DefaultClientJsonPacker;
import com.servlet.osf.json.JsonClientPacker;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.processer.listener.OSFClientDefaultListener;
import com.servlet.osf.processer.listener.OSFClientListener;
import lombok.Data;

/**
 * 公用客户端处理
 */
@Data
public abstract class AbstractClient implements OSFClient {
    private String clientName;// 客户端名称
    private String messageFormat = "json";// 内容格式
    private String encoding = "utf-8";// 字符集
    private String okCode = "0";// 成功码值
    //    private String locale = "zh_CN";// 当地语言
    private int connectTimeOut = 10000;// 10秒
    private int readTimeOut = 90000;// 90秒
    private String messagePackerClazz;// 信息解析器类名
    private static JsonClientPacker messagePacker;// 信息解析器
    private String listenerClazz;// 监听器类型
    private static OSFClientListener listener;// 监听器

    @Override
    public String clientName() {
        return clientName;
    }

    @Override
    public String okCode() {
        return okCode;
    }


    public RespServiceMsg call(ReqServiceMsg request, OSFClientContext context) {
        listener().start(request, context);
        // 获取请求字符串
        String reqStr = messagePacker().pack(request, context);
        context.setReqStr(reqStr);
        // 调用远程服务之前
        listener().beforeCall(reqStr, context);
        // 不同客户端对应的调用方式
        String respStr = send(reqStr, context);
        // 调用服务结束后
        listener().afterCall(respStr, context);
        // 解析响应后
        RespServiceMsg response = messagePacker().unpack(respStr, context);
        // 解析响应内容后
        listener().afterUnpack(response, context);
        // 调用远程服务结束
        listener().end(context);

        return response;
    }

    /**
     * 不同客户端的调用方式
     *
     * @param reqStr  请求内容
     * @param context 上下文
     * @return 响应内容
     */
    protected abstract String send(String reqStr, OSFClientContext context);

    @Override
    public JsonClientPacker messagePacker() {
        if (messagePacker == null) {
            if (StrUtil.isBlank(messagePackerClazz)) {
                messagePacker = new DefaultClientJsonPacker();
            } else {
                try {
                    messagePacker = (JsonClientPacker) Class.forName(messagePackerClazz).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    throw new OSFException(OSFException.JSONPACKER_REFLECT_ERROR, e);
                }
            }
        }

        return messagePacker;
    }

    @Override
    public OSFClientListener listener() {
        if (listener == null) {
            if (StrUtil.isBlank(listenerClazz)) {
                listener = new OSFClientDefaultListener();
            } else {
                try {
                    listener = (OSFClientListener) Class.forName(listenerClazz).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    throw new OSFException(OSFException.LISTENER_REFLECT_ERROR, e);
                }
            }
        }

        return listener;
    }

    public OSFClientContext createContext() {
        OSFClientContext context = new OSFClientContext();
        OSFClientContext.setContext(context);
        context.setClient(this);

        return context;
    }

    @Override
    public void close() {
        // TODO 执行清除缓存的操作
    }
}
