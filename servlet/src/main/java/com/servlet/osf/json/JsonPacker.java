package com.servlet.osf.json;

import com.servlet.osf.OSFContext;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * JSON处理器
 */
public interface JsonPacker {
    /**
     * 封装
     *
     * @param response 响应信息
     * @param context  上下文
     * @return 封装结果
     * @throws OSFException OSF异常
     */
    String pack(RespServiceMsg response, OSFContext context) throws OSFException;

    /**
     * 解析
     *
     * @param reqJsonStr 请求json字符串
     * @param context    上下文
     * @return 解析结果
     * @throws OSFException OSF异常
     */
    ReqServiceMsg unpack(String reqJsonStr, OSFContext context) throws OSFException;
}
