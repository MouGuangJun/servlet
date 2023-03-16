package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReqPageInfo extends PageInfo {
    private String PAGE_UP_DOWN;
}
