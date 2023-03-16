package com.servlet.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.exception.OSFException;

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

    public static String getProvidTranNo() {
        Date date = new Date();
        String systemDate = DateUtil.format(date, "yyyyMMdd");
        String systemTime = DateUtil.format(date, "HHmmss");


        return null;
    }
}
