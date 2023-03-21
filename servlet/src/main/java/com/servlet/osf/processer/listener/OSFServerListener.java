package com.servlet.osf.processer.listener;

import com.servlet.osf.OSFContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * 服务端监听器
 */
public interface OSFServerListener {
    // 执行OSF服务开始
    void start(OSFContext context);

    // 执行业务逻辑开始
    void beforeExecute(ReqServiceMsg req, OSFContext context);

    // 执行业务逻辑结束
    void afterExecute(RespServiceMsg resp, OSFContext context);

    // 返回给客户端之前
    void beforeSend(String payload, OSFContext context);

    // 执行OSF服务结束
    void end(OSFContext context);
}
