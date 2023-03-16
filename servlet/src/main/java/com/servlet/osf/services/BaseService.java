package com.servlet.osf.services;

import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.entity.request.BaseRequest;
import com.servlet.osf.entity.response.BaseResponse;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

public interface BaseService {

    BaseRequest getRequestObj();

    BaseResponse getResponseObj();

    RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException;
}
