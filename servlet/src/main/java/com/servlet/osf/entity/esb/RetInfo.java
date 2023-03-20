package com.servlet.osf.entity.esb;

import com.servlet.osf.constant.OSFCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 提示信息
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RetInfo {
    private String RET_CODE;// 服务返回码
    private String RET_INFO;// 服务返回信息


    public void setSuccess() {
        setInfo(OSFCode.SUCCESS, "处理成功！");
    }

    public void setError() {
        setInfo(OSFCode.ERROR, "处理失败!");
    }

    public void setInfo(String code, String message) {
        this.RET_CODE = code;
        this.RET_INFO = message;
    }
}
