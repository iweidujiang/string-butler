package io.github.iweidujiang.stringbutler.enums;

/**
 * 📦 去除空格的策略枚举
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public enum TrimStrategy {

    /**
     * 去除首尾空格（默认）
     */
    ALL,

    /**
     * 仅去除开头空格
     */
    START_ONLY,

    /**
     * 仅去除末尾空格
     */
    END_ONLY,

    /**
     * 去除所有空白字符（包括中间的空格）
     */
    ALL_WHITESPACE,

    /**
     * 智能去除 - 保留单词间的单个空格
     */
    SMART
}
