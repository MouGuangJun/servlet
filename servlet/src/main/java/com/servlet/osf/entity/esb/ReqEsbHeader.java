package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReqEsbHeader extends EsbHeader {
    private String SYS_EXTEND;
}
