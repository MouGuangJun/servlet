package com.servlet.osf.services.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.servlet.osf.client.OSFClient;
import com.servlet.osf.client.OSFClientManager;
import com.servlet.osf.entity.esb.ReqAppHeader;
import com.servlet.osf.entity.esb.ReqEsbHeader;
import com.servlet.osf.entity.request.ArrayRequest;
import com.servlet.osf.entity.request.SubRequest;
import com.servlet.osf.entity.request.TradeDemoRequest;
import com.servlet.osf.json.AbstractJsonPacker;
import com.servlet.osf.message.ReqServiceMsg;
import com.servlet.osf.message.RespServiceMsg;

import java.util.Collections;
import java.util.HashSet;

public class TradeClientDemo {

    public static void main(String[] args) {
        OSFClient client = OSFClientManager.getManager().getClient("defaultClient");
        ReqServiceMsg request = new ReqServiceMsg();
        ReqEsbHeader esbHeader = new ReqEsbHeader();
        ReqAppHeader appHeader = new ReqAppHeader();
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
        request.setEsbHeader(esbHeader);

        appHeader.setLEGAL_CODE("999999");
        appHeader.setSUB_SYS_ID("CCMS0");
        appHeader.setBRANTH_ID("53030200");
        appHeader.setTRAN_TELLER("CCMS0");
        appHeader.setCONSUM_TRAN_DATE("20230314");
        appHeader.setCONSUM_TRAN_TIME("162257892");
        request.setAppHeader(appHeader);

        TradeDemoRequest requestObj = new TradeDemoRequest();
        requestObj.setNAME("张三");
        SubRequest subRequest = new SubRequest();
        subRequest.setADDRESS("云南省昆明市");
        requestObj.setSUBREQUEST(subRequest);

        ArrayRequest arrayRequest = new ArrayRequest();
        arrayRequest.setTYPE("中文类型");

        HashSet<ArrayRequest> set = new HashSet<>();
        set.add(arrayRequest);
        requestObj.setOBJECT_ARRAY(Collections.singletonList(set));

        ArrayRequest arrayRequest1 = new ArrayRequest();
        arrayRequest1.setTYPE("英文类型");
        requestObj.setSTRING_ARRAY(Collections.singletonList(arrayRequest1).toArray(new ArrayRequest[0]));

        request.setBody(requestObj);
        client.respPayloadClazz("com.servlet.osf.entity.response.TradeDemoResponse");
        RespServiceMsg response = client.call(request);
        try {
            System.out.println(AbstractJsonPacker.mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
