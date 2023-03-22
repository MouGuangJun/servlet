package com.servlet.osf.entity.response;

import com.servlet.osf.annotation.OSFMetaField;
import lombok.Data;

@Data
public class AsyncTradeDemoResponse {
    @OSFMetaField
    private String key;
    @OSFMetaField
    private String value;
}
