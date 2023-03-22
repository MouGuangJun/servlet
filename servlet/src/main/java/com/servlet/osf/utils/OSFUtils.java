package com.servlet.osf.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.constant.SystemId;
import com.servlet.osf.exception.OSFException;
import com.servlet.utils.SerialHelper;

import java.util.Date;
import java.util.Map;

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
        return getTradeNo("DEFINED_PROVID_TRAN");
    }

    /**
     * 获取全局流水号
     *
     * @return 全局流水号
     */
    public static String getTxLogNo() {
        return getTradeNo("DEFINED_SERSEQ");
    }

    /**
     * 获取交易流水号
     *
     * @param tableName 表名
     * @return 交易流水号
     */
    private static String getTradeNo(String tableName) {
        Date date = new Date();
        String systemDate = DateUtil.format(date, "yyyyMMdd");
        String systemTime = DateUtil.format(date, "HHmmss");
        String serialNo = SerialHelper.getSimpleSerialNo(tableName, "SERIALNO");
        assert serialNo != null;
        String sortNo = serialNo.substring(8);
        String flag = SystemId.CCMS.getFlag();

        // 系统编码（6） + 日期（8） + 时间（6） + 标识（1） + 序号（8）
        return SystemId.SYSTEM_CODE + systemDate + systemTime + flag + sortNo;
    }


    /**
     * 获取对象中的属性值
     *
     * @param obj       对象
     * @param fieldName 属性名称
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj instanceof Map) {
            return ((Map<Object, Object>) obj).get(fieldName);
        }

        return ReflectUtil.getFieldValue(obj, fieldName);
    }
}
