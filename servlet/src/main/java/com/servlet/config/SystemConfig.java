package com.servlet.config;

import java.io.File;

/**
 * 系统配置文件
 */
public class SystemConfig {
    public static final String APP_HOME = "D:/IDEAWorkSpace/server/servlet/";
    public static final String RESOURCES_ROOT = "D:/IDEAWorkSpace/server/servlet/src/main/resources";

    public static String getClassDir(Class<?> clazz) {
        return (APP_HOME + "src/main/java/" + clazz.getPackage().getName()).replaceAll("([\\\\./])+", "\\" + File.separator);
    }

    public static String getResourcesRoot() {
        return (APP_HOME + "/src/main/resources").replaceAll("([\\\\./])+", "\\" + File.separator);
    }
}
