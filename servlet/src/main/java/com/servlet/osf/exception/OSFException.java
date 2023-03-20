package com.servlet.osf.exception;

import lombok.Getter;

/**
 * OSF执行异常
 */
public class OSFException extends RuntimeException {
    public static final int NORMAL_ERROR = 999999;
    // 业务导致的异常 1XXXXX
    public static final int SERVICE_NOT_FOUND = 101010;// 服务未找到

    // 程序异常 9XXXXX
    // 901XXX json解析异常
    public static final int JSON_PROCESSING = 901010;// json解析错误
    // 902XXX java反射异常
    public static final int REFLECT_NORMAL_ERROR = 902000;// 反射一般异常
    public static final int JSONPACKER_REFLECT_ERROR = 902010;// jsonpacker反射异常
    public static final int LISTENER_REFLECT_ERROR = 902020;// listener反射异常
    public static final int OSF_SERVICE_REFLECT_ERROR = 902030;// osf服务反射异常

    public static final int SERVICE_ENGINE_REFLECT_ERROR = 902020;// listener反射异常

    // 903XXX java IO异常
    public static final int RESP_WRITER_IO_ERROR = 903010;// servlet返回时writer io异常

    @Getter
    private final int code;

    public OSFException() {
        this(NORMAL_ERROR);
    }

    public OSFException(int code) {
        super();
        this.code = code;
    }

    public OSFException(String message) {
        this(NORMAL_ERROR, message);
    }

    public OSFException(int code, String message) {
        super(message);
        this.code = code;
    }

    public OSFException(String message, Throwable cause) {
        this(NORMAL_ERROR, message, cause);
    }

    public OSFException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public OSFException(Throwable cause) {
        this(NORMAL_ERROR, cause);
    }


    public OSFException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
