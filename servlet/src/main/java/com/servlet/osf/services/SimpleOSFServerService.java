package com.servlet.osf.services;

import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.entity.esb.AppHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import com.servlet.osf.entity.esb.RespPageInfo;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

public abstract class SimpleOSFServerService implements BaseService {

    protected RespPageInfo respPageInfo;

    protected static final String SYSTEM_CODE = "CCMS0";

    @Override
    public RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException {
        // 先校验请求json是否符合规范

        initField(request, context);

        return packResponse(request, context);
    }


    /**
     * 初始化服务需要用到的属性
     *
     * @param request 请求信息
     * @param context 上下文
     */
    private void initField(ReqServiceMsg request, OSFContext context) {
        respPageInfo = new RespPageInfo();
        AppHeader appHeader = request.getAppHeader();
        // 设置页码信息
        respPageInfo.setCURR_PAGE_NUM(appHeader.getPAGE_INFO().getCURR_PAGE_NUM());
        respPageInfo.setPER_PAGE_NUM(appHeader.getPAGE_INFO().getPER_PAGE_NUM());
    }

    /**
     * 封装OSF响应内容
     *
     * @param request 请求信息
     * @param context 上下文
     * @return OSF响应内容
     */
    private RespServiceMsg packResponse(ReqServiceMsg request, OSFContext context) {
        RespServiceMsg response = request.createResp();
        // 设置分页信息
        response.getAppHeader().setPAGE_INFO(respPageInfo);
        RespEsbHeader esbHeader = response.getEsbHeader();
        esbHeader.setPROVID_SYS_CODE(SYSTEM_CODE);
//        esbHeader.setSERVICE_RSP_SEQ();

        return null;
    }
}
