package com.servlet.osf.json;

import com.servlet.osf.OSFClientContext;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * JSON处理器（客户端）
 */
public interface JsonClientPacker {

    /**
     * 封装
     *
     * @param request 请求信息
     * @param context 上下文
     * @return 封装结果
     * @throws OSFException OSF异常
     */
    String pack(ReqServiceMsg request, OSFClientContext context) throws OSFException;

    /**
     * 解析
     *
     * @param respJsonStr 响应json字符串
     * @param context     上下文
     * @return 解析结果
     * @throws OSFException OSF异常
     */
    RespServiceMsg unpack(String respJsonStr, OSFClientContext context) throws OSFException;
}
