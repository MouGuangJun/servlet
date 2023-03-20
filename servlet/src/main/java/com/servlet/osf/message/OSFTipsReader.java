package com.servlet.osf.message;

import java.util.Collection;

/**
 * OSF提示信息读取器
 */
public interface OSFTipsReader {

    /**
     * 读取
     *
     * @param messages 信息
     * @return 读取结果
     */
    String read(Collection<String> messages);
}
