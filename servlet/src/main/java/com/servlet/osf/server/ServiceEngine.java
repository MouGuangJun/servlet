package com.servlet.osf.server;

import com.servlet.osf.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * 服务执行引擎
 */
public interface ServiceEngine {

    /**
     * 执行业务逻辑
     *
     * @param request 请求信息
     * @param context 上下文
     * @return 响应信息
     * @throws OSFException OSF异常
     */
    RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException;
}
