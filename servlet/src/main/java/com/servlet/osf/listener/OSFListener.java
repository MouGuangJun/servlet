package com.servlet.osf.listener;

import com.servlet.osf.OSFContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * 监听器
 */
public interface OSFListener {
    // 执行OSF服务开始
    void start(OSFContext context);

    // 执行业务逻辑开始
    void beforeExecute(ReqServiceMsg req, OSFContext context);

    // 执行业务逻辑结束，测试的req不希望被修改
    void afterExecute(ReqServiceMsg req, RespServiceMsg resp, OSFContext context);

    // 返回给客户端之前
    void beforeSend(String payload, OSFContext context);

    // 执行OSF服务结束
    void afterSend(OSFContext context);
}
