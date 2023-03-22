package com.servlet.osf.constant;

/**
 * OSF提示码值
 */
public class OSFCode {

    // 0XXXXX 成功的状态
    public static final String SUCCESS = "000000";// 通用成功

    // 1XXXXX - 3XXXXX业务错误
    public static final String REQ_PAYLOAD_ERROR = "100010";// 请求报文错误


    // 9XXXXX 系统错误
    public static final String SERVER_BUSY = "900010";// 超出流量控制

    public static final String ERROR = "999999";// 通用失败
    // 服务返回状态
    public static final String RET_STATUS_SUCCESS = "S";// 成功
    public static final String RET_STATUS_FAILED = "F";// 失败

    // 文件标识
    public static final String FILE_FLAG_NONE = "0";// 无文件
    public static final String FILE_FLAG = "1";// 有文件
}
