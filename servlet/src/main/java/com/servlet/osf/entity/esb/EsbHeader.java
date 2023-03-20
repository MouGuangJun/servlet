package com.servlet.osf.entity.esb;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.osf.message.ReqServiceMsg;
import lombok.Data;

/**
 * ESB头
 */
@Data
public class EsbHeader {
    private String SERVICE_NAME;// 服务编码
    private String SERVICE_VERSION;// 服务编码版本号
    private String SCENES_CODE;// 场景编码
    private String SCENES_VERSION;// 场景编码版本号
    private String CHANNEL_CODE;// 发起渠道标识
    private String CONSUM_REQ_DATE;// 请求系统日期
    private String CONSUM_REQ_TIME;// 请求系统时间
    private String CONSUM_SYS_CODE;// 服务请求方标识
    private String SERVICE_REQ_SEQ;// 请求系统流水号
    private String GLOBAL_SEQ;// 全局流水号
    private String SRC_ENC_CODE;// 源加密节点
    private String DEST_ENC_NODE;// 目标加密节点
    private String FILE_PATH;// 文件标识
    private String FILE_FLAG;// 文件路径


    public static void main(String[] args) throws JsonProcessingException {
        ReqServiceMsg reqServiceMsg = new ReqServiceMsg();
        ReqEsbHeader esbHeader = new ReqEsbHeader();
        esbHeader.setSERVICE_NAME("P01Q0001");
        esbHeader.setSERVICE_VERSION("1.0.0");
        esbHeader.setSCENES_CODE("25");
        esbHeader.setSCENES_VERSION("1.0.0");
        esbHeader.setCONSUM_SYS_CODE("CCMS0");
        esbHeader.setCHANNEL_CODE("010304");
        esbHeader.setCONSUM_REQ_DATE("20230314");
        esbHeader.setCONSUM_REQ_TIME("162257892");
        esbHeader.setSERVICE_REQ_SEQ("CCMS02023031003284510000502");
        esbHeader.setGLOBAL_SEQ("GCCMS02023031003284510000502");
        esbHeader.setFILE_FLAG("0");
        reqServiceMsg.setEsbHeader(esbHeader);

        ReqAppHeader appHeader = new ReqAppHeader();
        appHeader.setLEGAL_CODE("999999");
        appHeader.setSUB_SYS_ID("CCMS0");
        appHeader.setBRANTH_ID("53030200");
        appHeader.setTRAN_TELLER("CCMS0");
        appHeader.setCONSUM_TRAN_DATE("20230314");
        appHeader.setCONSUM_TRAN_TIME("162257892");
        reqServiceMsg.setAppHeader(appHeader);

        ObjectMapper mapper = new ObjectMapper();
        //屏蔽get方法的序列化
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        //设置任何属性可见
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        System.out.println(mapper.writeValueAsString(reqServiceMsg));
    }

}
