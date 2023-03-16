package com.servlet.osf.services;

import com.servlet.osf.annotation.OSFService;
import com.servlet.osf.entity.request.BaseRequest;
import com.servlet.osf.entity.request.TradeDemoRequest;
import com.servlet.osf.entity.response.BaseResponse;
import com.servlet.osf.entity.response.TradeDemoResponse;

@OSFService(name = "TradeDemo", code = "P01Q000125")
public class TradeDemo extends SimpleOSFServerService {
    @Override
    public BaseRequest getRequestObj() {
        return new TradeDemoRequest();
    }

    @Override
    public BaseResponse getResponseObj() {
        return new TradeDemoResponse();
    }

}
