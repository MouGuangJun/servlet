package com.servlet.osf.services.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.servlet.osf.entity.request.ArrayRequest;
import com.servlet.osf.entity.request.SubRequest;
import com.servlet.osf.entity.request.TradeDemoRequest;
import com.servlet.osf.entity.response.TradeDemoResponse;
import com.servlet.osf.json.AbstractJsonPacker;

import java.util.Collections;
import java.util.HashSet;

public class TradeClientDemo {

    public static void main(String[] args) {
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

        SimpleOSFClientService client = new SimpleOSFClientService("P01Q0001", "25");
        client.setReqPayload(requestObj);
        client.setRespClazz(TradeDemoResponse.class);

        String respCode = client.sentMessage();
        System.out.println(respCode);
        try {
            System.out.println(AbstractJsonPacker.mapper.writeValueAsString(client.getRespPayload()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
