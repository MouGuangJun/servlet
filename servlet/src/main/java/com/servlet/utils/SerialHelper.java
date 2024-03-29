package com.servlet.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.servlet.config.SystemConfig;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 自动获取当前的最大流水号主键
 */
public class SerialHelper {

    /**
     * 获取当前最大的流水号（不使用数据的方法）
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param prefix     前缀
     * @return 最大的流水号
     */
    public static String getSimpleSerialNo(String tableName, String columnName, String prefix) {
        return prefix + getSimpleSerialNo(tableName, columnName);
    }

    /**
     * 获取当前最大的流水号（不使用数据的方法）
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 最大的流水号
     */
    public static synchronized String getSimpleSerialNo(String tableName, String columnName) {
        if (StrUtil.isBlank(tableName) || StrUtil.isBlank(columnName)) {
            return null;
        }

        // 转换为大写
        tableName = tableName.toUpperCase();
        columnName = columnName.toUpperCase();

        String serialPath = SystemConfig.getResourcesRoot() + File.separator + "serial.properties";
        // 设置文件路径
        Props prop = Props.getProp(serialPath, StandardCharsets.UTF_8);
        // 需要匹配的名字
        String name = tableName + "_" + columnName;
        String value = prop.getStr(name);

        String serial;// 新的流水号
        // 不存在对应的key值，进行初始化操作
        String rightDate = DateUtil.format(new Date(), "yyyyMMdd");
        if (value == null || !value.substring(0, 8).equals(rightDate)) {
            serial = getSerialByNum(rightDate, 1L);
            // 保存的操作
            prop.setProperty(name, serial);
        } else {
            value = value.substring(8);
            long oldNum = Long.parseLong(value);
            // 获取新的流水号
            serial = getSerialByNum(rightDate, oldNum + 1L);
            // 保存的操作
            prop.setProperty(name, serial);
        }
        // 储存到文件中
        prop.store(serialPath);

        return serial;
    }

    /**
     * 通过数字获取yyyyMMdd + 8位数的固定长度值
     *
     * @param num 数字
     * @return 固定长度值
     */
    private static String getSerialByNum(Long num) {
        return getSerialByNum(DateUtil.format(new Date(), "yyyyMMdd"), num);
    }

    /**
     * 通过数字获取yyyyMMdd + 8位数的固定长度值
     *
     * @param date 时间
     * @param num  数字
     * @return 固定长度值
     */
    private static String getSerialByNum(String date, Long num) {
        String numStr = String.valueOf(num);// 获取字符串对象
        // if (numStr.length() > 8) throw new RuntimeException("流水号长度超长！");
        StringBuilder builder = new StringBuilder(date);
        for (int i = 0; i < 8 - numStr.length(); i++) {
            builder.append("0");
        }

        builder.append(numStr);
        return builder.toString();
    }
}
