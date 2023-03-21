package com.servlet.osf.services;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.servlet.osf.OSFContext;
import com.servlet.constant.SystemId;
import com.servlet.osf.processer.check.ReqPayloadCheck;
import com.servlet.osf.constant.OSFCode;
import com.servlet.osf.entity.esb.*;
import com.servlet.osf.entity.esb.ReqAppHeader;
import com.servlet.osf.entity.esb.RespAppHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.message.OSFTips;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.processer.parser.RespPayloadParser;
import com.servlet.osf.utils.OSFUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 简单的OSF服务端，实现process方法即可
 */
@Slf4j
public abstract class SimpleOSFServerService implements BaseService {

    protected RespPageInfo app_RESP_PAGE_INFO;// 响应分页信息
    protected String esb_SRC_ENC_CODE;// 源加密节点
    protected String esb_DEST_ENC_NODE;// 目标加密节点
    protected String esb_RET_STATUS;// 服务返回状态
    protected List<RetInfo> esb_RET_INFO_ARRAY = new ArrayList<>();// 服务返回数组
    protected RetInfo esb_RET_INFO = new RetInfo();// 服务返回信息
    protected String esb_FILE_FLAG;// 文件标识
    protected String esb_FILE_PATH;// 文件路径

    protected Object reqPayload;// 请求体

    protected Object respPayload;// 响应体

    @Override
    public RespServiceMsg execute(ReqServiceMsg request, OSFContext context) throws OSFException {
        RespServiceMsg response;
        try {
            // 初始化必要的字段信息
            initField(request, context);

            // 先校验请求json是否符合规范
            OSFTips tips = new ReqPayloadCheck(request, context).check();
            setSuccess();
            if (tips.isError()) {// 不符合规范，直接返回
                // 设置错误的提示信息
                setFailed(OSFCode.REQ_PAYLOAD_ERROR, tips.getMsg());
                return packResponse(request, context);
            }

            reqPayload = request.getBody();// 获取请求体

            proccess(request, context);// 执行具体的业务逻辑

            // 返回报文的解析 对返回字段的一些特殊处理
            if (respPayload != null) {
                new RespPayloadParser(respPayload, context).parse();
            }

            response = packResponse(request, context);
            // 设置响应体
            response.setBody(respPayload);
        } catch (Exception e) {
            log.error("OSF服务执行异常：", e);
            // 执行异常，封装错误信息
            RespEsbHeader esbHeader = new RespEsbHeader();
            esbHeader.setRET_STATUS(OSFCode.RET_STATUS_FAILED);
            esbHeader.setRET_INFO_ARRAY(Collections.singletonList(new RetInfo(OSFCode.ERROR, "处理异常！")));
            response = new RespServiceMsg();
            response.setEsbHeader(esbHeader);
            response.setAppHeader(new RespAppHeader());
        }

        return response;
    }

    /**
     * 具体的业务处理逻辑
     *
     * @param request 请求信息
     * @param context 上下文
     */
    protected abstract void proccess(ReqServiceMsg request, OSFContext context);

    // 设置状态为成功
    protected void setSuccess() {
        esb_RET_STATUS = OSFCode.RET_STATUS_SUCCESS;
        esb_RET_INFO.setSuccess();
    }

    protected void setSuccess(String code, String message) {
        esb_RET_STATUS = OSFCode.RET_STATUS_SUCCESS;
        esb_RET_INFO.setInfo(code, message);
    }

    // 设置状态为失败
    protected void setFailed(String code, String message) {
        esb_RET_INFO.setInfo(code, message);
        esb_RET_STATUS = OSFCode.RET_STATUS_FAILED;
    }


    protected void setFailed() {
        esb_RET_INFO.setSuccess();
        esb_RET_STATUS = OSFCode.RET_STATUS_FAILED;
    }


    /**
     * 初始化服务需要用到的属性
     *
     * @param request 请求信息
     * @param context 上下文
     */
    private void initField(ReqServiceMsg request, OSFContext context) {
        app_RESP_PAGE_INFO = new RespPageInfo();
        ReqAppHeader appHeader = request.getAppHeader();
        // 设置页码信息
        ReqPageInfo pageInfo = appHeader.getPAGE_INFO();
        if (pageInfo != null) {
            app_RESP_PAGE_INFO.setCURR_PAGE_NUM(pageInfo.getCURR_PAGE_NUM());
            app_RESP_PAGE_INFO.setPER_PAGE_NUM(pageInfo.getPER_PAGE_NUM());
        }

        esb_RET_INFO_ARRAY.add(esb_RET_INFO);
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
        Date date = new Date();
        String resTransDate = DateUtil.format(date, DatePattern.PURE_DATE_FORMAT);
        String resTransTime = DateUtil.format(date, DatePattern.PURE_TIME_FORMAT);
        String resTransNo = OSFUtils.getProvidTranNo();
        // APP Header
        RespAppHeader appHeader = response.getAppHeader();
        appHeader.setPROVID_TRAN_DATE(resTransDate);
        appHeader.setPROVID_TRAN_TIME(resTransTime);
        appHeader.setPROVID_TRAN_SEQ(resTransNo);
        // 设置分页信息
        appHeader.setPAGE_INFO(app_RESP_PAGE_INFO);

        // ESB Header
        RespEsbHeader esbHeader = response.getEsbHeader();
        esbHeader.setPROVID_SYS_CODE(SystemId.SYSTEM_CODE);
        esbHeader.setSERVICE_RSP_SEQ(resTransNo);

        esbHeader.setPROVID_RSP_DATE(resTransDate);
        esbHeader.setPROVID_RSP_TIME(resTransTime);
        esbHeader.setSRC_ENC_CODE(esb_SRC_ENC_CODE);
        esbHeader.setDEST_ENC_NODE(esb_DEST_ENC_NODE);
        esbHeader.setRET_STATUS(esb_RET_STATUS);
        esbHeader.setRET_INFO_ARRAY(esb_RET_INFO_ARRAY);
        esbHeader.setFILE_FLAG(esb_FILE_FLAG);
        esbHeader.setFILE_PATH(esb_FILE_PATH);

        return response;
    }
}
