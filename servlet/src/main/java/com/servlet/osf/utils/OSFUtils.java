package com.servlet.osf.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.constant.SystemId;
import com.servlet.osf.exception.OSFException;
import com.servlet.utils.SerialHelper;

import java.util.Date;

/**
 * OSF的工具类
 */
public class OSFUtils {
    /**
     * 创建对象
     *
     * @param errorCode 创建对象失败时返回的错误信息
     * @param className 类名
     * @return 对象实例
     */
    public static Object createObj(int errorCode, String className) {
        if (StrUtil.isBlank(className)) return null;

        try {
            return Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new OSFException(errorCode, e);
        }
    }

    /**
     * 获取交易流水号
     *
     * @return 交易流水号
     */
    public static String getProvidTranNo() {
        Date date = new Date();
        String systemDate = DateUtil.format(date, "yyyyMMdd");
        String systemTime = DateUtil.format(date, "HHmmss");
        String serialNo = SerialHelper.getSimpleSerialNo("DEFINED_PROVID_TRAN", "SERIALNO");
        assert serialNo != null;
        String sortNo = serialNo.substring(8);
        String flag = SystemId.CCMS.getFlag();

        // 系统编码（6） + 日期（8） + 时间（6） + 标识（1） + 序号（8）
        return SystemId.SYSTEM_CODE + systemDate + systemTime + flag + sortNo;
    }
}
