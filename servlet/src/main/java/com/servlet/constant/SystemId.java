package com.servlet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统和对应的标识
 */
@Getter
@AllArgsConstructor
public enum SystemId {
    CCMS("CCMS", "1"),
    GCMS("GCMS", "2"),
    CLMS("CLMS", "3"),
    AISA("AISA", "4");

    private final String name;
    private final String flag;

    public static final String SYSTEM_CODE = "CCMS0";
}
