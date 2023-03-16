package com.servlet.osf.server;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;
import com.servlet.osf.OSFManager;
import com.servlet.osf.annotation.OSFService;
import com.servlet.osf.services.BaseService;

import javax.servlet.ServletConfig;

public abstract class LoadSourceServerModel implements ServerModel {

    public static final String DEFAULT_PACKAGE = "com.servlet.osf.services";

    @Override
    public void load(ServletConfig config) throws OSFException {
        // 加载所有的服务
        String osfPackage = config.getInitParameter("OSFBasePackage");
        if (StrUtil.isBlank(osfPackage)) osfPackage = DEFAULT_PACKAGE;
        for (Class<?> clazz : ClassUtil.scanPackage(osfPackage)) {
            OSFService service;
            if ((service = clazz.getAnnotation(OSFService.class)) != null) {
                com.servlet.osf.entity.OSFService osfService = new com.servlet.osf.entity.OSFService();
                osfService.setCode(service.code());
                osfService.setName(service.name());
                osfService.setLabel(service.label());
                try {
                    osfService.setService((BaseService) clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new OSFException(OSFException.OSF_SERVICE_REFLECT_ERROR, e);
                }

                OSFManager.putService(osfService);
            }
        }

    }

    @Override
    public void distroy() throws OSFException {
        OSFManager.clear();
        OSFContext.removeContext();
    }
}
