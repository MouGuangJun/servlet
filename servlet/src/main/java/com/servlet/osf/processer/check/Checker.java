package com.servlet.osf.processer.check;

import com.servlet.osf.message.OSFTips;

/**
 * 业务信息检查器
 */
public interface Checker {
    /**
     * 检查
     *
     * @return 检查结果
     */
    OSFTips check();
}
