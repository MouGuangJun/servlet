package com.servlet.osf.message;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class OSFTips {

    enum Level {
        WARNING, ERROR
    }

    private Map<Level, String> messages = new LinkedHashMap<>();


    public void warning(String message) {
        messages.put(Level.WARNING, message);
    }

    public void error(String message) {
        messages.put(Level.ERROR, message);
    }

    public String getMessage() {
        return getMessage(Object::toString);
    }

    public String getMessage(OSFTipsReader reader) {
        return reader.read(messages.values());
    }

    public boolean haveMsg() {
        return messages.size() > 0;
    }

    public boolean isError() {
        return messages.containsKey(Level.ERROR);
    }
}
