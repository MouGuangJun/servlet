package com.servlet.osf.server;

import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.entity.OSFService;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import lombok.Data;

@Data
public class DefaultServiceEngine implements ServiceEngine {
    private OSFService osfService;


    @Override
    public RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException {

        return osfService.getService().execute(request, context);
    }
}
