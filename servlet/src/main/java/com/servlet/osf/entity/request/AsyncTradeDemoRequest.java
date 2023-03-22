package com.servlet.osf.entity.request;

import com.servlet.osf.annotation.OSFMetaField;
import lombok.Data;

@Data
public class AsyncTradeDemoRequest {
    @OSFMetaField
    private String NAME;
    @OSFMetaField
    private String ADDRESS;
}
