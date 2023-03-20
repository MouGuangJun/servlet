package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 响应应用头
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RespAppHeader extends AppHeader {
    private String PROVID_TRAN_DATE;// 应答账务日期
    private String PROVID_TRAN_TIME;// 应答账务时间
    private String PROVID_TRAN_SEQ;// 应答账务流水号
    private RespPageInfo PAGE_INFO;// 响应分页信息
}
