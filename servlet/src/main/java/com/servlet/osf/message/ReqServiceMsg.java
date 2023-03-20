package com.servlet.osf.message;

import cn.hutool.core.util.ReflectUtil;
import com.servlet.osf.entity.esb.ReqAppHeader;
import com.servlet.osf.entity.esb.ReqEsbHeader;
import com.servlet.osf.entity.esb.RespAppHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * OSF请求总信息
 */
@Data
public class ReqServiceMsg {
    // APP HEADER不需要拷贝的属性
    private final List<String> APP_IGNORE_FILEDS = Arrays.asList("SUB_SYS_ID", "PAGE_INFO", "CONFIRM_FLAG", "CONFIRM_STATUS");
    // ESB HEADER不需要拷贝的属性
    private final List<String> ESB_IGNORE_FILEDS = Arrays.asList("SYS_EXTEND", "SRC_ENC_CODE", "DEST_ENC_NODE", "FILE_PATH", "FILE_FLAG");
    private ReqEsbHeader EsbHeader;
    private Object Body;
    private ReqAppHeader AppHeader;

    public RespServiceMsg createResp() {
        RespServiceMsg response = new RespServiceMsg();
        // App Header
        RespAppHeader appHeader = new RespAppHeader();
        for (Field field : ReflectUtil.getFields(ReqAppHeader.class)) {
            // 不需要复制的属性
            if (APP_IGNORE_FILEDS.contains(field.getName())) {
                continue;
            }
            ReflectUtil.setFieldValue(appHeader, field.getName(),
                    ReflectUtil.getFieldValue(AppHeader, field));
        }

        // Esb Header
        RespEsbHeader esbHeader = new RespEsbHeader();
        for (Field field : ReflectUtil.getFields(ReqEsbHeader.class)) {
            // 不需要复制的属性
            if (ESB_IGNORE_FILEDS.contains(field.getName())) {
                continue;
            }
            ReflectUtil.setFieldValue(esbHeader, field.getName(),
                    ReflectUtil.getFieldValue(EsbHeader, field));
        }

        response.setAppHeader(appHeader);
        response.setEsbHeader(esbHeader);

        return response;
    }
}
