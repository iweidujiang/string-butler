package io.github.iweidujiang.stringbutler.transformation;

/**
 * 📦 字符串截断转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class TruncateRule implements TransformationRule {
    private final int maxLength;
    private final String suffix;
    private final boolean preserveWords;

    /**
     * 构造函数
     *
     * @param maxLength 最大长度
     * @param suffix 截断后添加的后缀
     * @param preserveWords 是否保持单词完整性
     */
    public TruncateRule(int maxLength, String suffix, boolean preserveWords) {
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength must be non-negative");
        }
        this.maxLength = maxLength;
        this.suffix = suffix != null ? suffix : "";
        this.preserveWords = preserveWords;
    }

    /**
     * 简化构造函数（不保持单词完整性）
     *
     * @param maxLength 最大长度
     * @param suffix 截断后添加的后缀
     */
    public TruncateRule(int maxLength, String suffix) {
        this(maxLength, suffix, false);
    }

    /**
     * 默认构造函数（使用省略号作为后缀）
     *
     * @param maxLength 最大长度
     */
    public TruncateRule(int maxLength) {
        this(maxLength, "...", true);
    }

    @Override
    public String transform(String value) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }

        if (!preserveWords) {
            // 简单截断
            return value.substring(0, maxLength - suffix.length()) + suffix;
        }

        // 智能截断：保持单词完整性
        return smartTruncate(value, maxLength, suffix);
    }

    private String smartTruncate(String value, int maxLength, String suffix) {
        int targetLength = maxLength - suffix.length();

        if (targetLength <= 0) {
            return suffix;
        }

        // 查找最后一个空格位置
        int lastSpace = value.lastIndexOf(' ', targetLength);
        int truncateAt;

        if (lastSpace > targetLength / 2) {
            // 如果找到空格且在合理位置，则在该空格处截断
            truncateAt = lastSpace;
        } else {
            // 否则在目标长度处截断
            truncateAt = targetLength;
        }

        return value.substring(0, truncateAt).trim() + suffix;
    }
}
