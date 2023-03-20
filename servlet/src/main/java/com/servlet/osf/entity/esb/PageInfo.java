package com.servlet.osf.entity.esb;

import lombok.Data;

/**
 * 分页基础信息
 */
@Data
public class PageInfo {
    private String CURR_PAGE_NUM;// 当前页码
    private String PER_PAGE_NUM;// 每页条数
}
