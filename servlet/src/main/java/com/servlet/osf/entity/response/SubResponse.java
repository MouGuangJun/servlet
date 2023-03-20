package com.servlet.osf.entity.response;

import com.servlet.osf.annotation.OSFMetaField;
import com.servlet.osf.constant.OSFType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubResponse {
    @OSFMetaField(type = OSFType.NUMBER, length = @OSFMetaField.Length(value = 2, decimal = 2))
    private BigDecimal number;
}
