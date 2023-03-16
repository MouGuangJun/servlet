package com.servlet.osf.entity.request;

import com.servlet.osf.annotation.OSFMetaField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TradeDemoRequest extends BaseRequest {
    @OSFMetaField
    private String NAME;
}
