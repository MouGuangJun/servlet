package com.servlet.osf.client;


import com.servlet.osf.constant.ClientType;
import com.servlet.osf.json.JsonClientPacker;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.processer.listener.OSFClientListener;

/**
 * OSF客户端
 */
public interface OSFClient {

    // 客户端名称
    String clientName();

    ClientType type();

    // 成功码值
    String okCode();

    // json解析器
    JsonClientPacker messagePacker();

    /**
     * 发送请求
     *
     * @param request 请求信息
     * @return 响应信息
     */
    RespServiceMsg call(ReqServiceMsg request);

    /**
     * 发送请求
     *
     * @param request   请求信息
     * @param respClazz 响应对象字节码
     * @return 响应信息
     */
    RespServiceMsg call(ReqServiceMsg request, Class<?> respClazz);


    // 监听器
    OSFClientListener listener();

    // 释放资源
    void close();
}
