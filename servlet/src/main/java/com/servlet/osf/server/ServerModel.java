package com.servlet.osf.server;

import com.servlet.osf.exception.OSFException;
import com.servlet.osf.OSFContext;

import javax.servlet.ServletConfig;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 服务执行器
 */
public interface ServerModel {

    /**
     * 装载数据
     *
     * @param config Servlet配置信息
     * @throws OSFException OSF异常
     */
    void load(ServletConfig config) throws OSFException;

    /**
     * 执行服务
     *
     * @param in      输入流
     * @param out     输出流
     * @param context 上下文
     * @throws OSFException OSF异常
     */
    void service(InputStream in, OutputStream out, OSFContext context) throws OSFException;

    /**
     * 销毁缓存数据
     *
     * @throws OSFException OSF异常
     */
    void distroy() throws OSFException;
}
