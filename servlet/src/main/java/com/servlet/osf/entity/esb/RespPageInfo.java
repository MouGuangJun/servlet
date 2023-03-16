package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RespPageInfo extends PageInfo {
    private String TOTAL_NUM;
    private String TOTAL_PAGE_NUM;
}
