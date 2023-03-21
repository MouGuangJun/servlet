package com.servlet.osf.server;

import com.servlet.osf.OSFContext;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.processer.listener.OSFServerDefaultListener;
import com.servlet.osf.processer.listener.OSFServerListener;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.utils.OSFUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.ServletConfig;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 通用服务执行器
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractServerModel extends LoadSourceServerModel {
    protected OSFServerListener listener;
    protected ServiceEngine serviceEngine;

    @Override
    public void load(ServletConfig config) throws OSFException {
        super.load(config);
        String listener = config.getInitParameter("OSFListener");
        this.listener = (OSFServerListener) OSFUtils.createObj(OSFException.LISTENER_REFLECT_ERROR, listener);
        String serviceEngine = config.getInitParameter("OSFServiceEngine");
        this.serviceEngine = (ServiceEngine) OSFUtils.createObj(OSFException.SERVICE_ENGINE_REFLECT_ERROR, serviceEngine);

        defProcess();
    }

    /**
     * 当处理器为空时，默认处理器
     */
    private void defProcess() {
        if (this.listener == null) {
            this.listener = new OSFServerDefaultListener();
        }

        if (this.serviceEngine == null) {
            this.serviceEngine = new DefaultServiceEngine();
        }
    }

    @Override
    public void service(InputStream in, OutputStream out, OSFContext context) throws OSFException {
        listener.start(context);// 开始OSF服务
        ReqServiceMsg request = this.receive(in, context);// 接收请求报文
        listener.beforeExecute(request, context);// 执行服务前
        // 执行服务并获取响应结果
        RespServiceMsg response = serviceEngine.execute(request, context);
        listener.afterExecute(response, context);// 服务执行后
        this.send(response, out, context);// 通知客户端
    }

    protected abstract ReqServiceMsg receive(InputStream in, OSFContext context) throws OSFException;

    protected abstract void send(RespServiceMsg res, OutputStream out, OSFContext context) throws OSFException;
}
