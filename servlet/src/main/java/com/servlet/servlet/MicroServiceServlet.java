package com.servlet.servlet;

import com.servlet.osf.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.server.StringPayloadServerModel;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OSF服务入口Servlet
 */
@Slf4j
public class MicroServiceServlet extends HttpServlet {

    private StringPayloadServerModel serverModel;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().write("Not support".getBytes());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServletInputStream in = req.getInputStream();
             ServletOutputStream out = resp.getOutputStream()) {
            OSFContext context = serverModel.createOSFContext();// 创建上下文对象
            context.setClientAddress(req.getRemoteAddr());
            context.setReq(req);
            context.setResp(resp);
            serverModel.service(in, out, context);
        } catch (Exception e) {
            log.error("执行服务异常：", e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        serverModel.distroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            serverModel = new StringPayloadServerModel();
            serverModel.load(config);
        } catch (OSFException e) {
            log.error(config.getServletName() + "启动报错：", e);
        }

    }
}
