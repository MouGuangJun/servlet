package com.servlet.osf.entity.response;

import com.servlet.osf.annotation.OSFMetaField;
import com.servlet.osf.constant.OSFType;
import lombok.Data;

import java.util.List;

@Data
public class TradeDemoResponse {
    @OSFMetaField(length = @OSFMetaField.Length(18))
    private String name;
    @OSFMetaField(type = OSFType.NUMBER, length = @OSFMetaField.Length(value = 4, decimal = 2))
    private Double num;
    @OSFMetaField(type = OSFType.OBJECT)
    private SubResponse subResponse;
    @OSFMetaField(type = OSFType.OBJECT_ARRAY, actualType = SubResponse.class)
    private List<SubResponse> subResponses;
}
