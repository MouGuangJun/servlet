package com.servlet.osf.message;

import cn.hutool.core.util.ReflectUtil;
import com.servlet.osf.entity.esb.AppHeader;
import com.servlet.osf.entity.esb.ReqEsbHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import com.servlet.osf.entity.request.BaseRequest;
import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ReqServiceMsg {
    private ReqEsbHeader EsbHeader;
    private BaseRequest Body;
    private AppHeader AppHeader;

    public RespServiceMsg createResp() {
        RespServiceMsg response = new RespServiceMsg();
        RespEsbHeader esbHeader = new RespEsbHeader();
        for (Field field : ReflectUtil.getFields(RespEsbHeader.class)) {
            ReflectUtil.setFieldValue(esbHeader, field.getName(),
                    ReflectUtil.getFieldValue(EsbHeader, field));
        }

        response.setEsbHeader(esbHeader);
        response.setAppHeader(AppHeader);

        return response;
    }
}
