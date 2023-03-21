package com.servlet.osf.entity.esb;

import lombok.Data;

/**
 * ESB头
 */
@Data
public class EsbHeader {
    private String SERVICE_NAME;// 服务编码
    private String SERVICE_VERSION;// 服务编码版本号
    private String SCENES_CODE;// 场景编码
    private String SCENES_VERSION;// 场景编码版本号
    private String CHANNEL_CODE;// 发起渠道标识
    private String CONSUM_REQ_DATE;// 请求系统日期
    private String CONSUM_REQ_TIME;// 请求系统时间
    private String CONSUM_SYS_CODE;// 服务请求方标识
    private String SERVICE_REQ_SEQ;// 请求系统流水号
    private String GLOBAL_SEQ;// 全局流水号
    private String SRC_ENC_CODE;// 源加密节点
    private String DEST_ENC_NODE;// 目标加密节点
    private String FILE_PATH;// 文件标识
    private String FILE_FLAG;// 文件路径
}
