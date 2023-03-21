package com.servlet.osf.processer.listener;

import cn.hutool.core.date.DateUtil;
import com.servlet.osf.OSFContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * OSF服务执行监听器
 */
@Slf4j
public class OSFServerDefaultListener implements OSFServerListener {
    @Override
    public void start(OSFContext context) {
        log.info("执行OSF服务开始...");
    }

    @Override
    public void beforeExecute(ReqServiceMsg req, OSFContext context) {
        log.info("OSF服务端_收到：" + context.getReqJsonStr());
        log.info("执行业务逻辑开始：" + DateUtil.formatDateTime(new Date()));
    }

    @Override
    public void afterExecute(RespServiceMsg resp, OSFContext context) {
        log.info("执行业务逻辑结束：" + DateUtil.formatDateTime(new Date()));
    }

    @Override
    public void beforeSend(String payload, OSFContext context) {
        log.info("OSF服务端_发送：" + payload);
    }

    @Override
    public void end(OSFContext context) {
        log.info("OSF服务执行结束...");
    }
}
