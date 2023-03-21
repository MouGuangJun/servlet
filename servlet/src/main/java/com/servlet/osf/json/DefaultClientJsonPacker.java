package com.servlet.osf.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.MapType;
import com.servlet.osf.OSFClientContext;
import com.servlet.osf.entity.esb.RespAppHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

import java.util.HashMap;

/**
 * 默认解析JSON报文的处理器
 */
public class DefaultClientJsonPacker extends AbstractJsonPacker implements JsonClientPacker {

    @Override
    public String pack(ReqServiceMsg request, OSFClientContext context) throws OSFException {
        try {
            return mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new OSFException(OSFException.JSON_PROCESSING, e);
        }
    }

    @Override
    public RespServiceMsg unpack(String respJsonStr, OSFClientContext context) throws OSFException {
        RespServiceMsg response = new RespServiceMsg();
        try {
            // 解析JSON
            JsonNode node = mapper.readTree(respJsonStr);
            RespEsbHeader esbHeader = mapper.convertValue(node.get("EsbHeader"), RespEsbHeader.class);
            response.setEsbHeader(esbHeader);
            RespAppHeader appHeader = mapper.convertValue(node.get("AppHeader"), RespAppHeader.class);
            response.setAppHeader(appHeader);
            // 获取响应对象字节码
            Class<?> respClazz = context.getRespClazz();
            Object body;// 解析响应内容
            if (respClazz == null) {
                MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
                body = mapper.convertValue(node.get("Body"), mapType);
            } else {
                body = mapper.convertValue(node.get("Body"), respClazz);
            }
            response.setBody(body);
        } catch (JsonProcessingException e) {
            throw new OSFException(OSFException.JSON_PROCESSING, e);
        }

        return response;
    }
}
