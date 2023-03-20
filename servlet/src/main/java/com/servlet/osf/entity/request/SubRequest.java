package com.servlet.osf.entity.request;

import com.servlet.osf.annotation.OSFMetaField;
import lombok.Data;

@Data
public class SubRequest {
    @OSFMetaField(label = "地址", require = true)
    private String ADDRESS;
}
