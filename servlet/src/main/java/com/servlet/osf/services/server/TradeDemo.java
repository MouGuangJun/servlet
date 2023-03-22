package com.servlet.osf.services.server;

import com.servlet.osf.OSFContext;
import com.servlet.osf.annotation.OSFService;
import com.servlet.osf.entity.request.TradeDemoRequest;
import com.servlet.osf.entity.response.SubResponse;
import com.servlet.osf.entity.response.TradeDemoResponse;
import com.servlet.osf.message.ReqServiceMsg;

import java.math.BigDecimal;
import java.util.Collections;

@OSFService(name = "TradeDemo", code = "P01Q000125")
public class TradeDemo extends SimpleOSFServerService {
    @Override
    public Class<?> requestClazz() {
        return TradeDemoRequest.class;
    }

    @Override
    public Class<?> responseClazz() {
        return TradeDemoResponse.class;
    }

    @Override
    public void proccess(ReqServiceMsg request, OSFContext context) {
        System.out.println(reqPayload);
        System.out.println("执行对应的业务逻辑...");

        TradeDemoResponse response = new TradeDemoResponse();
        response.setName("响应体");
        response.setNum(12.446d);
        SubResponse subResponse = new SubResponse();
        subResponse.setNumber(new BigDecimal("14.567"));
        response.setSubResponse(subResponse);
        response.setSubResponse(subResponse);
        response.setSubResponses(Collections.singletonList(subResponse));

        respPayload = response;
    }
}
