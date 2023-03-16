package com.servlet.utils;

import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropUtils {
    public static String path;
    private static Properties properties;

    // 初始化
    static {
        load();
    }

    // 初始化
    private static boolean load() {
        if (properties != null) return true;// 已经加载过

        // 有文件的路径才进行加载
        if (StrUtil.isNotBlank(path)) {
            properties = new Properties();
            try (InputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(path)))) {
                properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    // 获取Properties
    public static Properties getProp() {
        if (load()) {
            return properties;
        }

        return null;
    }

    // 获取值
    public static String getValue(String key) {
        if (load()) {
            return properties.getProperty(key);
        }

        return null;
    }

    // 存储值
    public static void setValue(String key, String value) {
        if (load()) {
            try (FileOutputStream out = new FileOutputStream(path, false)) {
                properties.setProperty(key, value);
                properties.store(new OutputStreamWriter(out, StandardCharsets.UTF_8), "bill_info");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 文件复制
    public static void copy(String targetPath) {
        try (InputStream is = Files.newInputStream(new File(path).toPath());
             OutputStream os = Files.newOutputStream(new File(targetPath).toPath())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
