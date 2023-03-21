package com.servlet.osf.processer.listener;

import cn.hutool.core.date.DateUtil;
import com.servlet.osf.OSFClientContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

import java.util.Date;

/**
 * OSF默认客户端监听器
 */
public class OSFClientDefaultListener implements OSFClientListener {

    @Override
    public void start(ReqServiceMsg request, OSFClientContext context) {
        System.out.println("调用远程服务开始...");
    }

    @Override
    public void beforeCall(String reqStr, OSFClientContext context) {
        System.out.println("OSF客户端_发送：" + context.getReqStr());
        System.out.println("调用开始：" + DateUtil.formatDateTime(new Date()));
    }

    @Override
    public void afterCall(String respStr, OSFClientContext context) {
        System.out.println("调用结束：" + DateUtil.formatDateTime(new Date()));
        System.out.println("OSF客户端_收到：" + respStr);
    }

    @Override
    public void afterUnpack(RespServiceMsg response, OSFClientContext context) {
        System.out.println("解析报文结束...");
    }

    @Override
    public void end(OSFClientContext context) {
        System.out.println("调用远程服务结束...");
    }
}
