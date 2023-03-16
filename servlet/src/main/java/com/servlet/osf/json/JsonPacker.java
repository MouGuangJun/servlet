package com.servlet.osf.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

public interface JsonPacker {
    ObjectMapper mapper = new ObjectMapper();

    String pack(RespServiceMsg response, OSFContext context) throws OSFException;

    ReqServiceMsg unpack(String reqJsonStr, OSFContext context) throws OSFException;
}
