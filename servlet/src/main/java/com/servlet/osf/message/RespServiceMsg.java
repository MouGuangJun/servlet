package com.servlet.osf.message;

import com.servlet.osf.entity.esb.AppHeader;
import com.servlet.osf.entity.esb.RespEsbHeader;
import com.servlet.osf.entity.response.BaseResponse;
import lombok.Data;

@Data
public class RespServiceMsg {
    private RespEsbHeader EsbHeader;
    private BaseResponse Body;
    private AppHeader AppHeader;
}
