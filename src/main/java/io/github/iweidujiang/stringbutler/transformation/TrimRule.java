package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.enums.TrimStrategy;

/**
 * 📦 去除空格转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class TrimRule implements TransformationRule {
    private final TrimStrategy strategy;

    /**
     * 默认构造函数，使用ALL策略
     */
    public TrimRule() {
        this(TrimStrategy.ALL);
    }

    /**
     * 构造函数
     *
     * @param strategy 去除空格策略
     */
    public TrimRule(TrimStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String transform(String value) {
        if (value == null) {
            return null;
        }

        switch (strategy) {
            case START_ONLY:
                return value.replaceAll("^\\s+", "");
            case END_ONLY:
                return value.replaceAll("\\s+$", "");
            case ALL_WHITESPACE:
                return value.replaceAll("\\s+", "");
            case SMART:
                return smartTrim(value);
            default:
                return value.trim();
        }
    }

    private String smartTrim(String value) {
        // 去除首尾空格，并将中间的多个空格合并为一个
        return value.trim().replaceAll("\\s+", " ");
    }
}
