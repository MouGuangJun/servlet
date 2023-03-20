package com.servlet.osf.message;

import com.servlet.osf.entity.esb.RespAppHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import lombok.Data;

/**
 * OSF响应总信息
 */
@Data
public class RespServiceMsg {
    private RespEsbHeader EsbHeader;
    private Object Body;
    private RespAppHeader AppHeader;
}
