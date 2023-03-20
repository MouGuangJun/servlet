package com.servlet.osf.entity.esb;

import lombok.Data;

/**
 * 授权信息
 */
@Data
public class AuthInfo {
    private String AUTH_CODE;// 授权代码
    private String AUTH_INFO;// 授权信息
}
