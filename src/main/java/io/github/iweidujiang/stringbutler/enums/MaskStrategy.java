package io.github.iweidujiang.stringbutler.enums;

/**
 * 📦 字符串掩码策略枚举
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public enum MaskStrategy {
    /**
     * 掩码中间部分
     */
    MIDDLE,

    /**
     * 掩码开头部分
     */
    START,

    /**
     * 掩码末尾部分
     */
    END,

    /**
     * 全部掩码
     */
    FULL,

    /**
     * 只显示首尾，中间掩码
     */
    FIRST_LAST
}
