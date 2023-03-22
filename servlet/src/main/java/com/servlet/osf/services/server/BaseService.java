package com.servlet.osf.services.server;

import com.servlet.osf.OSFContext;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * OSF服务
 */
public interface BaseService {

    /**
     * 请求对象字节码
     *
     * @return 请求对象字节码
     */
    Class<?> requestClazz();

    /**
     * 响应对象字节码
     *
     * @return 响应对象字节码
     */
    Class<?> responseClazz();

    /**
     * 执行服务
     *
     * @param request 请求信息
     * @param context 上下文
     * @return 响应信息
     * @throws OSFException OSF异常
     */
    RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException;
}
