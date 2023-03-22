package com.servlet.osf.entity.request;

import com.servlet.osf.annotation.OSFMetaField;
import lombok.Data;

@Data
public class ArrayRequest {
    @OSFMetaField/*(require = true)*/
    private String TYPE;
}
