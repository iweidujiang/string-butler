package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.utils.PatternCache;

import java.util.regex.Pattern;

/**
 * 📦 正则表达式替换转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class RegexReplaceRule implements TransformationRule {
    private final Pattern pattern;
    private final String replacement;

    /**
     * 构造函数（使用缓存）
     *
     * @param pattern 正则表达式模式字符串
     * @param replacement 替换字符串
     */
    public RegexReplaceRule(String pattern, String replacement) {
        this.pattern = PatternCache.getPattern(pattern);
        this.replacement = replacement;
    }

    /**
     * 构造函数（使用预编译的Pattern）
     *
     * @param pattern 预编译的正则表达式
     * @param replacement 替换字符串
     */
    public RegexReplaceRule(Pattern pattern, String replacement) {
        this.pattern = pattern;
        this.replacement = replacement;
    }

    @Override
    public String transform(String value) {
        if (value == null) {
            return null;
        }
        return pattern.matcher(value).replaceAll(replacement);
    }
}
