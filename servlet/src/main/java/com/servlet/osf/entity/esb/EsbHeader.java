package com.servlet.osf.entity.esb;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.osf.message.ReqServiceMsg;
import lombok.Data;

@Data
public class EsbHeader {
    private String SERVICE_NAME;
    private String SERVICE_VERSION;
    private String SCENES_CODE;
    private String SCENES_VERSION;
    private String CHANNEL_CODE;
    private String CONSUM_REQ_DATE;
    private String CONSUM_REQ_TIME;
    private String CONSUM_SYS_CODE;
    private String SERVICE_REQ_SEQ;
    private String GLOBAL_SEQ;
    private String SRC_ENC_CODE;
    private String DEST_ENC_NODE;
    private String FILE_PATH;
    private String FILE_FLAG;


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

        AppHeader appHeader = new AppHeader();
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
