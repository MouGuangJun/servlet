package com.servlet.osf.entity.esb;

import lombok.Data;

import java.util.List;

@Data
public class AppHeader {
    private String LEGAL_CODE;
    private String SUB_SYS_ID;
    private String TRAN_CODE;
    private String BRANTH_ID;
    private String TRAN_TELLER;
    private String CONSUM_TRAN_DATE;
    private String CONSUM_TRAN_TIME;
    private String CONSUM_TRAN_SEQ;
    private String AUTH_TELLER;
    private String AUTH_REQ_SEQ;
    private String AUTH_FLAG;
    private String AUTH_STATUS;
    private List<AuthInfo> AUTH_INFO_ARRAY;
    private String CONFIRM_STATUS;
    private String CONFIRM_FLAG;
    private List<ConfirmInfo> CONFIRM_INFO_ARRAY;
    private PageInfo PAGE_INFO;
}
