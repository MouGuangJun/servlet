package com.servlet.osf.server;

import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * 服务执行引擎
 */
public interface ServiceEngine {

    // 执行业务逻辑
    RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException;
}
