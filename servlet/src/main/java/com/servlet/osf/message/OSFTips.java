package com.servlet.osf.message;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OSF提示信息
 */
@Data
public class OSFTips {

    enum Level {
        WARNING, ERROR
    }

    private Map<Level, Set<String>> messages = new HashMap<>();


    public void warn(String message) {
        appendMsg(Level.WARNING, message);
    }

    public void error(String message) {
        appendMsg(Level.ERROR, message);
    }

    public String getMsg() {
        return getMsg(null, Object::toString);
    }

    public String getWarnMsg(OSFTipsReader reader) {
        return getMsg(Level.WARNING, reader);
    }

    public String getErrorMsg(OSFTipsReader reader) {
        return getMsg(Level.ERROR, reader);
    }

    /**
     * 添加错误信息
     *
     * @param level   错误级别
     * @param message 错误信息
     */
    private void appendMsg(Level level, String message) {
        if (messages.containsKey(level)) {
            messages.get(level).add(message);
            return;
        }

        messages.put(level, CollectionUtil.newHashSet(message));
    }

    /**
     * 读取错误信息
     *
     * @param level  错误级别 值为null时，读取所有信息
     * @param reader 读取器
     * @return 错误信息
     */
    private String getMsg(Level level, OSFTipsReader reader) {
        if (level == null) {
            return reader.read(messages.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        }

        if (messages.containsKey(level)) {
            return reader.read(messages.get(level));
        }

        return null;
    }

    public boolean haveMsg() {
        return messages.size() > 0;
    }

    public boolean isError() {
        return messages.containsKey(Level.ERROR);
    }
}
