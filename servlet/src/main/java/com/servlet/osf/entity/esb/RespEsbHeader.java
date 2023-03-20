package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 响应ESB头
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RespEsbHeader extends EsbHeader {
    private String PROVID_SYS_CODE;// 服务提供方标识
    private String SERVICE_RSP_SEQ;// 应答系统流水号
    private String PROVID_RSP_DATE;// 应答系统日期
    private String PROVID_RSP_TIME;// 应答系统时间
    private String RET_STATUS;// 服务返回状态
    private List<RetInfo> RET_INFO_ARRAY;// 服务返回信息数组
}
