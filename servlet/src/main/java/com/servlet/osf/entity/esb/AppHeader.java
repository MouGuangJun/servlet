package com.servlet.osf.entity.esb;

import lombok.Data;

import java.util.List;

/**
 * 应用头
 */
@Data
public class AppHeader {
    private String LEGAL_CODE;// 法人编码
    private String TRAN_CODE;// 交易码
    private String BRANTH_ID;// 机构号
    private String TRAN_TELLER;// 柜员号
    private String CONSUM_TRAN_DATE;// 请求账务日期
    private String CONSUM_TRAN_TIME;// 请求账务时间
    private String CONSUM_TRAN_SEQ;// 请求账务流水号
    private String AUTH_TELLER;// 授权操作员
    private String AUTH_REQ_SEQ;// 授权请求流水号
    private String AUTH_FLAG;// 授权标识
    private String AUTH_STATUS;// 授权状态
    private List<AuthInfo> AUTH_INFO_ARRAY;// 数组
    private String CONFIRM_STATUS;// 确认状态
    private String CONFIRM_FLAG;// 确认标识
    private List<ConfirmInfo> CONFIRM_INFO_ARRAY;// 确认信息数组
}
