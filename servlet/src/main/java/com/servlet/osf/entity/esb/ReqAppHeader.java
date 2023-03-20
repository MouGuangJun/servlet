package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 请求应用头
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReqAppHeader extends AppHeader {
    private String SUB_SYS_ID;// 子系统编码
    private ReqPageInfo PAGE_INFO;// 请求分页信息
}
