package com.servlet.osf.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.servlet.osf.OSFClientContext;
import com.servlet.osf.constant.ClientType;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.Charset;

/**
 * http客户端
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HttpClient extends AbstractClient {
    private String url;// 请求地址

    @Override
    public ClientType type() {
        return ClientType.HTTP;
    }

    @Override
    public RespServiceMsg call(ReqServiceMsg request) {
        OSFClientContext context = createContext();
        context.setRespClazz(getRespPayloadClazz());

        return call(request, context);
    }

    public RespServiceMsg call(ReqServiceMsg request, OSFClientContext context) {
        return super.call(request, context);
    }

    @Override
    protected String send(String reqStr, OSFClientContext context) {
        try (HttpResponse response = HttpRequest.of(getUrl(), Charset.forName(getEncoding()))
                .setConnectionTimeout(getConnectTimeOut())// 连接超时时间
                .setReadTimeout(getReadTimeOut())// 读取超时时间
                .setMethod(Method.POST)// POST请求
                .body(reqStr)// 设置请求内容
                .header("Content-Type", "application/" + getMessageFormat() + ";charset=" + getEncoding())
                .execute()) {

            return response.body();
        }
    }
}
