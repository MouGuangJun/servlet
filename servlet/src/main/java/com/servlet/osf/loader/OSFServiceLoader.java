package com.servlet.osf.loader;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.osf.OSFManager;
import com.servlet.osf.annotation.OSFService;
import com.servlet.osf.exception.OSFException;
import com.servlet.osf.services.BaseService;
import lombok.Data;

/**
 * 加载所有的OSF服务
 */
@Data
public class OSFServiceLoader implements OSFLoader {
    private String OSFPackage;

    @Override
    public void load() {
        if (StrUtil.isBlank(OSFPackage)) return;

        for (Class<?> clazz : ClassUtil.scanPackage(OSFPackage)) {
            OSFService service;
            // 初始化服务信息
            if ((service = clazz.getAnnotation(OSFService.class)) != null) {
                com.servlet.osf.entity.OSFService OSFService = new com.servlet.osf.entity.OSFService();
                OSFService.setCode(service.code());
                OSFService.setName(service.name());
                OSFService.setLabel(service.label());
                try {
                    OSFService.setService((BaseService) clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new OSFException(OSFException.OSF_SERVICE_REFLECT_ERROR, e);
                }

                // 初始化元数据信息
                OSFMetaFieldLoader loader = new OSFMetaFieldLoader();
                loader.setOSFService(OSFService);
                loader.load();

                OSFManager.putService(OSFService);
            }
        }
    }
}
