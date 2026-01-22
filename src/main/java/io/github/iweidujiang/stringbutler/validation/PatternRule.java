package io.github.iweidujiang.stringbutler.validation;

import io.github.iweidujiang.stringbutler.utils.PatternCache;

import java.util.regex.Pattern;

/**
 * 📦 正则表达式验证规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class PatternRule implements ValidationRule {
    private final Pattern pattern;
    private final String errorMessage;

    /**
     * 构造函数（使用缓存）
     *
     * @param pattern 正则表达式模式字符串
     * @param errorMessage 错误信息
     */
    public PatternRule(String pattern, String errorMessage) {
        this.pattern = PatternCache.getPattern(pattern);
        this.errorMessage = errorMessage;
    }

    /**
     * 构造函数（使用默认错误信息）
     *
     * @param pattern 正则表达式模式
     */
    public PatternRule(String pattern) {
        this(pattern, "String does not match required pattern");
    }

    /**
     * 构造函数（使用预编译的Pattern）
     *
     * @param pattern 预编译的正则表达式
     * @param errorMessage 错误信息
     */
    public PatternRule(Pattern pattern, String errorMessage) {
        this.pattern = pattern;
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean validate(String value) {
        if (value == null) {
            return false;
        }
        return pattern.matcher(value).matches();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
