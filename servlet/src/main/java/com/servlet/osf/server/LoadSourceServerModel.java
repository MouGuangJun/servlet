package com.servlet.osf.server;

import cn.hutool.core.util.StrUtil;
import com.servlet.osf.OSFManager;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.processer.loader.OSFServiceLoader;

import javax.servlet.ServletConfig;

/**
 * 加载静态资源
 */
public abstract class LoadSourceServerModel implements ServerModel {

    public static final String DEFAULT_PACKAGE = "com.servlet.osf.services";

    @Override
    public void load(ServletConfig config) throws OSFException {
        // 加载所有的服务
        String osfPackage = config.getInitParameter("OSFBasePackage");
        if (StrUtil.isBlank(osfPackage)) osfPackage = DEFAULT_PACKAGE;
        // 获取服务加载器
        OSFServiceLoader loader = new OSFServiceLoader();
        loader.setOSFPackage(osfPackage);
        // 执行加载的操作
        loader.load();
    }

    @Override
    public void distroy() throws OSFException {
        OSFManager.clear();
    }
}
