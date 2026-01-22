package io.github.iweidujiang.stringbutler.enums;

/**
 * 📦 空白字符串处理策略
 * <p>
 * 👤 作者：苏渡苇
 * 🔗 公众号：苏渡苇
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public enum BlankStrategy {
    /**
     * 使用默认值替换空白字符串
     */
    USE_DEFAULT,

    /**
     * 当遇到空白字符串时抛出异常
     */
    THROW_EXCEPTION,

    /**
     * 忽略空白字符串，保持原样
     */
    IGNORE,

    /**
     * 记录警告信息但继续处理
     */
    LOG_WARNING
}
