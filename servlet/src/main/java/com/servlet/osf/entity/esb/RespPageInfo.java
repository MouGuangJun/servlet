package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应分页信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RespPageInfo extends PageInfo {
    private String TOTAL_NUM;// 总条数
    private String TOTAL_PAGE_NUM;// 总页数
}
