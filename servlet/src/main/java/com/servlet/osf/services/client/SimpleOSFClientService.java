package com.servlet.osf.services.client;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.constant.SystemId;
import com.servlet.osf.client.OSFClient;
import com.servlet.osf.client.OSFClientManager;
import com.servlet.osf.constant.OSFCode;
import com.servlet.osf.entity.esb.*;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;
import com.servlet.osf.utils.OSFUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 简单的OSF客户端，实现process方法即可
 */
@Data
@NoArgsConstructor
public class SimpleOSFClientService {
    // 默认配置信息
    private final static String DEFAULT_CLIENT = "defaultClient";// 默认客户端
    private final static String SERVICE_VERSION = "1.0.0";// 服务编码版本号
    private final static String SCENES_VERSION = "1.0.0";// 服务场景版本号
    //    private final static String SYS_CODE = SystemId.SYSTEM_CODE;// 服务请求方标识
    private final static String CHANNEL_CODE = "010304";// 发起渠道标识
    private final static String LEGAL_CODE = "999999";// 法人编码

    // ESB头 ESB_HEADER
    protected String esb_SERVICE_NAME;// 服务编码（TODO 设值）
    //    protected String esb_SERVICE_VERSION;// 服务编码版本号
    protected String esb_SCENES_CODE;// 场景编码（TODO 设值）
    //    protected String esb_SCENES_VERSION;// 场景编码版本号
//    protected String esb_CHANNEL_CODE;// 发起渠道标识
//    protected String esb_CONSUM_REQ_DATE;// 请求系统日期
//    protected String esb_CONSUM_REQ_TIME;// 请求系统时间
//    protected String esb_CONSUM_SYS_CODE;// 服务请求方标识
    protected String esb_SERVICE_REQ_SEQ;// 请求系统流水号
    protected String esb_GLOBAL_SEQ;// 全局流水号
    protected String esb_SRC_ENC_CODE;// 源加密节点
    protected String esb_DEST_ENC_NODE;// 目标加密节点
    protected String esb_FILE_PATH;// 文件标识
    protected String esb_FILE_FLAG;// 文件路径
    protected String esb_SYS_EXTEND;// 扩展域

    // 应用头 APP_HEADER
//    protected String app_LEGAL_CODE;// 法人编码
    protected String app_TRAN_CODE;// 交易码
    protected String app_BRANTH_ID;// 机构号
    protected String app_TRAN_TELLER;// 柜员号
    //    protected String app_CONSUM_TRAN_DATE;// 请求账务日期
//    protected String app_CONSUM_TRAN_TIME;// 请求账务时间
    protected String app_CONSUM_TRAN_SEQ;// 请求账务流水号
    protected String app_AUTH_TELLER;// 授权操作员
    protected String app_AUTH_REQ_SEQ;// 授权请求流水号
    protected String app_AUTH_FLAG;// 授权标识
    protected String app_AUTH_STATUS;// 授权状态
    protected List<AuthInfo> app_AUTH_INFO_ARRAY = new ArrayList<>();// 数组
    protected AuthInfo app_AUTH_INFO;// 不为空时，会将该数据添加到数组中
    protected String app_CONFIRM_STATUS;// 确认状态
    protected String app_CONFIRM_FLAG;// 确认标识
    protected List<ConfirmInfo> app_CONFIRM_INFO_ARRAY = new ArrayList<>();// 确认信息数组
    protected ConfirmInfo app_CONFIRM_INFO;// 不为空时，会将该数据添加到数组中
    protected ReqPageInfo app_PAGE_INFO = new ReqPageInfo();// 请求分页信息
    //    protected String app_SUB_SYS_ID = SYS_CODE;// 子系统编码
    private final Date rightDate = new Date();
    private final String REQ_DATE = DateUtil.format(rightDate, "yyyyMMdd");// 请求日期
    private final String REQ_TIME = DateUtil.format(rightDate, "HHmmssSSS");// 请求时间

    protected List<RetInfo> retInfos = new ArrayList<>();// 所有响应信息

    protected Object reqPayload;// 请求载体（TODO 设值）

    protected Object respPayload;// 响应载体

    protected Class<?> respClazz;// 响应载体字节码

    protected RespEsbHeader respEsbHeader;// ESB响应头

    protected RespAppHeader respAppHeader;// ESB应用头

    public SimpleOSFClientService(String serviceName, String scenesCode) {
        esb_SERVICE_NAME = serviceName;
        esb_SCENES_CODE = scenesCode;
    }

    /**
     * 调用远程服务端
     *
     * @return 返回码值信息
     * 成功：{@link OSFCode#RET_STATUS_SUCCESS}
     * 失败：{@link OSFCode#RET_STATUS_FAILED}
     */
    public String sentMessage() {
        // 初始化基本信息
        app_BRANTH_ID = "53030200";// 机构号
        app_TRAN_TELLER = SystemId.SYSTEM_CODE;// 柜员号
        esb_FILE_FLAG = OSFCode.FILE_FLAG_NONE;// 文件标识

        // 柜员号|机构号不能为空
        if (StrUtil.isBlank(app_BRANTH_ID) || StrUtil.isBlank(app_TRAN_TELLER)) {
            retInfos.add(new RetInfo(OSFCode.ERROR, "柜员号|机构号不能为空！！"));

            return OSFCode.RET_STATUS_FAILED;
        }

        // 获取客户端对象，调用远程服务
        OSFClient client = OSFClientManager.getManager().getClient(DEFAULT_CLIENT);
        ReqServiceMsg reqServiceMsg = new ReqServiceMsg();
        reqServiceMsg.setBody(reqPayload);// 设置请求载体

        // 应用头
        ReqAppHeader appHeader = new ReqAppHeader();
        packAppHeader(appHeader);
        reqServiceMsg.setAppHeader(appHeader);
        // Esb头
        ReqEsbHeader esbHeader = new ReqEsbHeader();
        packEsbHeader(esbHeader);
        reqServiceMsg.setEsbHeader(esbHeader);

        // 调用OSF服务
        RespServiceMsg response = client.call(reqServiceMsg, respClazz);
        // 获取响应信息
        respEsbHeader = response.getEsbHeader();
        respPayload = response.getBody();
        respAppHeader = response.getAppHeader();
        // 响应提示信息
        retInfos = respEsbHeader.getRET_INFO_ARRAY();

        return respEsbHeader.getRET_STATUS();
    }


    /**
     * 封装应用头
     *
     * @param appHeader 应用头
     */
    protected void packAppHeader(ReqAppHeader appHeader) {
        appHeader.setLEGAL_CODE(LEGAL_CODE);
        appHeader.setSUB_SYS_ID(SystemId.SYSTEM_CODE);
        appHeader.setTRAN_CODE(app_TRAN_CODE);
        appHeader.setBRANTH_ID(app_BRANTH_ID);
        appHeader.setTRAN_TELLER(app_TRAN_TELLER);
        appHeader.setCONSUM_TRAN_DATE(REQ_DATE);
        appHeader.setCONSUM_TRAN_TIME(REQ_TIME);
        appHeader.setCONSUM_TRAN_SEQ(app_CONSUM_TRAN_SEQ);
        appHeader.setAUTH_TELLER(app_AUTH_TELLER);
        appHeader.setAUTH_REQ_SEQ(app_AUTH_REQ_SEQ);
        appHeader.setAUTH_FLAG(app_AUTH_FLAG);
        appHeader.setAUTH_STATUS(app_AUTH_STATUS);

        // AUTH_INFO_ARRAY 授权数组
        if (app_AUTH_INFO != null) {
            app_AUTH_INFO_ARRAY.add(app_AUTH_INFO);
        }
        appHeader.setAUTH_INFO_ARRAY(app_AUTH_INFO_ARRAY);

        // CONFIRM_INFO_ARRAY 确认信息数组
        if (app_CONFIRM_INFO != null) {
            app_CONFIRM_INFO_ARRAY.add(app_CONFIRM_INFO);
        }
        appHeader.setCONFIRM_STATUS(app_CONFIRM_STATUS);
        appHeader.setCONFIRM_FLAG(app_CONFIRM_FLAG);
        appHeader.setCONFIRM_INFO_ARRAY(app_CONFIRM_INFO_ARRAY);

        // PAGE_INFO 分页信息封装
        appHeader.setPAGE_INFO(app_PAGE_INFO);
    }

    /**
     * 封装Esb头
     *
     * @param esbHeader Esb头
     */
    protected void packEsbHeader(ReqEsbHeader esbHeader) {
        esbHeader.setSERVICE_NAME(esb_SERVICE_NAME);
        esbHeader.setSERVICE_VERSION(SERVICE_VERSION);
        esbHeader.setSCENES_CODE(esb_SCENES_CODE);
        esbHeader.setSCENES_VERSION(SCENES_VERSION);
        esbHeader.setCONSUM_SYS_CODE(SystemId.SYSTEM_CODE);
        esbHeader.setCHANNEL_CODE(CHANNEL_CODE);
        esbHeader.setCONSUM_REQ_DATE(REQ_DATE);
        esbHeader.setCONSUM_REQ_TIME(REQ_TIME);
        // 获取全局流水号
        if (StrUtil.isBlank(esb_SERVICE_REQ_SEQ)) {
            esb_SERVICE_REQ_SEQ = OSFUtils.getTxLogNo();
            esb_GLOBAL_SEQ = "G" + esb_SERVICE_REQ_SEQ;
        }
        esbHeader.setSERVICE_REQ_SEQ(esb_SERVICE_REQ_SEQ);
        esbHeader.setGLOBAL_SEQ(esb_GLOBAL_SEQ);
        esbHeader.setSRC_ENC_CODE(esb_SRC_ENC_CODE);
        esbHeader.setDEST_ENC_NODE(esb_DEST_ENC_NODE);
        esbHeader.setFILE_PATH(esb_FILE_PATH);
        esbHeader.setFILE_FLAG(esb_FILE_FLAG);
        esbHeader.setSYS_EXTEND(esb_SYS_EXTEND);
    }
}
