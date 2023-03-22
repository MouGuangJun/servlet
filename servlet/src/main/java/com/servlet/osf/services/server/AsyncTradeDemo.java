package com.servlet.osf.services.server;

import com.servlet.osf.OSFContext;
import com.servlet.osf.annotation.OSFService;
import com.servlet.osf.entity.request.AsyncTradeDemoRequest;
import com.servlet.osf.entity.request.SubRequest;
import com.servlet.osf.entity.request.TradeDemoRequest;
import com.servlet.osf.entity.response.AsyncTradeDemoResponse;
import com.servlet.osf.entity.response.TradeDemoResponse;
import com.servlet.osf.message.ReqServiceMsg;

@OSFService(name = "AsyncTradeDemo", code = "P08N00070X")
public class AsyncTradeDemo extends AsyncOSFServerService {

    @Override
    protected Object respPayload() {
        AsyncTradeDemoResponse response = new AsyncTradeDemoResponse();
        response.setKey("key");
        response.setValue("value");

        return response;
    }

    @Override
    protected Object asyncReqPayload() {
        return asyncReqPayload;
    }

    @Override
    public Class<?> asyncResponseClazz() {
        return TradeDemoResponse.class;
    }

    @Override
    public boolean asyncExec(ReqServiceMsg request, OSFContext context) throws Exception {

        System.out.println(reqPayload);
        System.out.println(context);

        TradeDemoRequest asyncRequestObj = new TradeDemoRequest();
        asyncRequestObj.setNAME("李四");
        SubRequest subRequest = new SubRequest();
        subRequest.setADDRESS("四川省成都市");
        asyncRequestObj.setSUBREQUEST(subRequest);

        asyncReqPayload = asyncRequestObj;

        return false;
    }

    @Override
    public Class<?> requestClazz() {
        return AsyncTradeDemoRequest.class;
    }

    @Override
    public Class<?> responseClazz() {
        return AsyncTradeDemoResponse.class;
    }
}
