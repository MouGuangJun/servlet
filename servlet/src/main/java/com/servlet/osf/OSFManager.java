package com.servlet.osf;

import com.servlet.osf.entity.OSFService;

import java.util.HashMap;
import java.util.Map;

public class OSFManager {
    private static final Map<String, OSFService> services = new HashMap<>();

    public static void putService(OSFService service) {
        services.put(service.getCode(), service);
    }

    public static OSFService getService(String code) {
        return services.get(code);
    }

    public static void clear() {
        services.clear();
    }
}
