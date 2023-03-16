package com.servlet.osf.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.servlet.osf.config.OSFConfig.*;

/**
 * 执行结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteResult {

    private String code;// 码值

    public String message;// 信息

    public static ExecuteResult createOKResult() {
        return createResult(successCode, successMsg);
    }

    public static ExecuteResult createErrResult() {
        return createResult(errorCode, errorMsg);
    }

    public static ExecuteResult createResult(String code, String message) {
        return new ExecuteResult(code, message);
    }
}
