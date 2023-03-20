package com.servlet.osf.entity.esb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 请求ESB头
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReqEsbHeader extends EsbHeader {
    private String SYS_EXTEND;// 扩展域
}
