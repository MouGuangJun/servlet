package com.servlet.osf.server;

import cn.hutool.core.io.IoUtil;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.json.DefaultJsonPacker;
import com.servlet.osf.json.JsonPacker;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.utils.OSFUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * 字符串类型服务执行器
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StringPayloadServerModel extends AbstractServerModel {
    protected JsonPacker jsonPacker;

    @Override
    public void load(ServletConfig config) throws OSFException {
        super.load(config);
        String jsonPackerClass = config.getInitParameter("JsonPackerClass");
        this.jsonPacker = (JsonPacker) OSFUtils.createObj(OSFException.JSONPACKER_REFLECT_ERROR, jsonPackerClass);

        defProcess();
    }

    private void defProcess() {
        if (this.jsonPacker == null) {
            this.jsonPacker = new DefaultJsonPacker();
        }
    }

    @Override
    public void service(InputStream in, OutputStream out, OSFContext context) throws OSFException {
        context.setEncoding(StandardCharsets.UTF_8);
        super.service(in, out, context);
    }

    @Override
    protected ReqServiceMsg receive(InputStream in, OSFContext context) throws OSFException {
        return jsonPacker.unpack(IoUtil.read(in, context.getEncoding()), context);
    }

    @Override
    protected void send(RespServiceMsg res, OutputStream out, OSFContext context) throws OSFException {
        String payload = jsonPacker.pack(res, context);// 返回载荷
        listener.beforeSend(payload, context);// 返回客户端前执行的操作
        try {
            out.write(payload.getBytes(context.getEncoding()));
        } catch (IOException e) {
            throw new OSFException(OSFException.RESP_WRITER_IO_ERROR, e);
        }
        listener.afterSend(context);
    }

    public OSFContext createOSFContext() {
        OSFContext context = new OSFContext();
        OSFContext.setContext(context);
        context.setServerModel(this);
        try {
            context.setServerAddress(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return context;
    }
}
