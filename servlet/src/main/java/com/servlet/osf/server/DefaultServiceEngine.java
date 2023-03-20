package com.servlet.osf.server;

import com.servlet.osf.OSFContext;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import lombok.Data;

/**
 * 默认服务执行引擎
 */
@Data
public class DefaultServiceEngine implements ServiceEngine {

    @Override
    public RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException {

        return context.getOsfService().getService().execute(request, context);
    }
}
