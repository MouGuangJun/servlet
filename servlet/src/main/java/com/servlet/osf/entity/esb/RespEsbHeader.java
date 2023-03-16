package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RespEsbHeader extends EsbHeader {
    private String PROVID_SYS_CODE;
    private String SERVICE_RSP_SEQ;
    private String PROVID_RSP_DATE;
    private String PROVID_RSP_TIME;
    private String RET_STATUS;
    private List<RetInfo> RET_INFO_ARRAY;
}
