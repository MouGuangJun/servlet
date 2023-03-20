package com.servlet.osf.entity.request;

import com.servlet.osf.annotation.OSFMetaField;
import com.servlet.osf.constant.OSFType;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class TradeDemoRequest {
    @OSFMetaField(label = "名称", require = true, length = @OSFMetaField.Length(3))
    private String NAME;
    @OSFMetaField(type = OSFType.NUMBER, length = @OSFMetaField.Length(value = 3, decimal = 2))
    private Double NUMBER;
    @OSFMetaField(type = OSFType.OBJECT)
    private SubRequest SUBREQUEST = new SubRequest();
    @OSFMetaField(type = OSFType.OBJECT)
    private Map<String, String> MAP;
    @OSFMetaField(type = OSFType.PRIMITIVE_ARRAY)
    private List<Set<Integer>> PRIMITIVE_ARRAY;
    @OSFMetaField(type = OSFType.OBJECT_ARRAY, actualType = ArrayRequest.class)
    private List<Set<ArrayRequest>> OBJECT_ARRAY;
    @OSFMetaField(type = OSFType.OBJECT_ARRAY, actualType = ArrayRequest.class)
    private ArrayRequest[] STRING_ARRAY;
}
