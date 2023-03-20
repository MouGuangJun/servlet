package com.servlet.osf.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.servlet.osf.OSFContext;
import com.servlet.osf.OSFManager;
import com.servlet.osf.entity.OSFService;
import com.servlet.osf.entity.esb.ReqAppHeader;
import com.servlet.osf.entity.esb.ReqEsbHeader;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

/**
 * 默认解析JSON报文的处理器
 */
public class DefaultJsonPacker implements JsonPacker {
    static {
        //屏蔽get方法的序列化
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        //设置任何属性可见
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // 生成 module
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(new PageInfoSerializer());
//        module.addDeserializer(PageInfo.class, new PageInfoDeserializer());
        // 注册 module
//        mapper.registerModule(module);
    }

    @Override
    public String pack(RespServiceMsg response, OSFContext context) throws OSFException {
        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new OSFException(OSFException.JSON_PROCESSING, e);
        }
    }

    @Override
    public ReqServiceMsg unpack(String reqJsonStr, OSFContext context) throws OSFException {
        ReqServiceMsg request = new ReqServiceMsg();
        try {
            context.setReqJsonStr(reqJsonStr);
            // 解析JSON
            JsonNode node = mapper.readTree(reqJsonStr);
            ReqEsbHeader esbHeader = mapper.convertValue(node.get("EsbHeader"), ReqEsbHeader.class);
            request.setEsbHeader(esbHeader);
            ReqAppHeader appHeader = mapper.convertValue(node.get("AppHeader"), ReqAppHeader.class);
            request.setAppHeader(appHeader);
            // 获取对应的服务
            String serviceCode = esbHeader.getSERVICE_NAME() + esbHeader.getSCENES_CODE();
            OSFService service = OSFManager.getService(serviceCode);
            // 服务不存在，抛出异常
            if (service == null) {
                throw new OSFException(OSFException.SERVICE_NOT_FOUND, "服务：[" + serviceCode + "]不存在！");
            }

            context.setOsfService(service.clone());
            Object requestObj = service.getService().getRequestObj();// 请求信息
            // 解析报文体信息
            Object body = mapper.convertValue(node.get("Body"), requestObj.getClass());
            request.setBody(body);

            // 设置请求信息
            context.setRequest(request);
        } catch (JsonProcessingException e) {
            throw new OSFException(OSFException.JSON_PROCESSING, e);
        }

        return request;
    }
}
