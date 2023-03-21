package com.servlet.osf.processer.listener;

import com.servlet.osf.OSFClientContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * 客户端监听器
 */
public interface OSFClientListener {
    /**
     * 调用远程服务之前
     *
     * @param request 请求信息
     * @param context 上下文
     */
    void start(ReqServiceMsg request, OSFClientContext context);

    /**
     * 调用服务之前
     *
     * @param reqStr  请求字符串
     * @param context 上下文
     */
    void beforeCall(String reqStr, OSFClientContext context);

    /**
     * 调用远程服务之后
     *
     * @param respStr 响应字符串
     * @param context 上下文
     */
    void afterCall(String respStr, OSFClientContext context);

    /**
     * 将响应内容解析后
     *
     * @param response 响应信息
     * @param context  上下文
     */
    void afterUnpack(RespServiceMsg response, OSFClientContext context);

    /**
     * 调用远程服务结束
     *
     * @param context 上下文
     */
    void end(OSFClientContext context);
}
