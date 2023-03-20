package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 请求分页信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReqPageInfo extends PageInfo {
    private String PAGE_UP_DOWN;// 上翻/下翻标识
}
