package com.servlet.osf.entity;

import com.servlet.osf.exception.OSFException;
import com.servlet.osf.services.server.BaseService;
import lombok.Data;

/**
 * 具体执行的服务
 */
@Data
public class OSFService implements Cloneable {
    private String name;// 服务名称
    private String code;// 服务码
    private String label;// 描述
    private BaseService service;// 执行服务
    private OSFMetaField reqMetaField;// 请求元数据
    private OSFMetaField respMetaField;// 响应元数据

    @Override
    public OSFService clone() {
        try {
            OSFService osfService = (OSFService) super.clone();
            // 克隆需要执行的服务
            osfService.setService(service.getClass().newInstance());

            return osfService;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new OSFException(OSFException.REFLECT_NORMAL_ERROR, e);
        }
    }
}
