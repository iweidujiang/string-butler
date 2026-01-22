package io.github.iweidujiang.stringbutler.validation;

import java.util.regex.Pattern;

/**
 * 📦 URL格式验证规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class UrlRule implements ValidationRule {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp)://" +                      // 协议
                    "([a-zA-Z0-9]([a-zA-Z0-9\\-]*[a-zA-Z0-9])?\\.)+" + // 域名
                    "[a-zA-Z]{2,}" +                         // 顶级域名
                    "(:[0-9]+)?" +                           // 端口
                    "(/.*)?$"                                // 路径
    );

    private final String errorMessage;

    /**
     * 构造函数
     *
     * @param errorMessage 错误信息
     */
    public UrlRule(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 默认构造函数
     */
    public UrlRule() {
        this("Invalid URL format");
    }

    @Override
    public boolean validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return URL_PATTERN.matcher(value.trim()).matches();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
