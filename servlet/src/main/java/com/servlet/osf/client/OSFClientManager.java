package com.servlet.osf.client;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.servlet.config.SystemConfig;
import com.servlet.osf.exception.OSFException;
import com.servlet.utils.XMLUtils;
import lombok.var;
import org.dom4j.Element;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * OSF客户端管理器
 */
public class OSFClientManager {

    // 客户端
    private static final Map<String, OSFClient> clients = new HashMap<>();

    // 单例模式
    private static final OSFClientManager manager;

    static {
        manager = new OSFClientManager();
        scanClients();// 扫描所有的客户端
    }

    private OSFClientManager() {

    }

    /**
     * 扫描客户端
     */
    private static void scanClients() {
        String path = SystemConfig.getResourcesRoot() + File.separator + "osfclients.xml";
        var root = XMLUtils.getDocument(path).getRootElement();
        for (Element child : XMLUtils.getChildElements(root)) {
            Map<String, String> configInfo = XMLUtils.getAttributes(child);
            newOSFClient(configInfo);
        }
    }

    /**
     * 创建OSFClient
     *
     * @param configInfo 配置信息
     */
    private static void newOSFClient(Map<String, String> configInfo) {
        String type = configInfo.get("type");// 客户端类型
        if (StrUtil.isBlank(type) || "http".equals(type)) {// 暂时只支持http
            HttpClient client = new HttpClient();
            Class<? extends HttpClient> clazz = client.getClass();
            Arrays.stream(ReflectUtil.getFields(clazz)).filter(f -> configInfo.containsKey(f.getName()))
                    .forEach(c -> ReflectUtil.setFieldValue(client, c, configInfo.get(c.getName())));

            // 客户端的名称不能为空
            if (StrUtil.isBlank(client.clientName())) {
                throw new OSFException(OSFException.CLIENT_NAME_EMPTY, "客户端的名称不能为空！");
            }

            // 放入缓存
            clients.put(client.clientName(), client);
        }
    }

    /**
     * 获取客户端管理器
     *
     * @return 客户端管理器
     */
    public static OSFClientManager getManager() {
        return manager;
    }

    /**
     * 获取客户端
     *
     * @param clientName 客户端名称
     * @return 客户端
     */
    public OSFClient getClient(String clientName) {
        return clients.get(clientName);
    }
}
