package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.enums.MaskStrategy;

/**
 * 📦 字符串掩码转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class MaskRule implements TransformationRule {
    private final MaskStrategy strategy;
    private final int visibleStart;
    private final int visibleEnd;
    private final char maskChar;

    /**
     * 构造函数
     *
     * @param strategy 掩码策略
     * @param visibleStart 开头可见字符数
     * @param visibleEnd 末尾可见字符数
     * @param maskChar 掩码字符
     */
    public MaskRule(MaskStrategy strategy, int visibleStart, int visibleEnd, char maskChar) {
        this.strategy = strategy;
        this.visibleStart = visibleStart;
        this.visibleEnd = visibleEnd;
        this.maskChar = maskChar;
    }

    /**
     * 简化构造函数（使用星号作为掩码字符）
     *
     * @param strategy 掩码策略
     * @param visibleStart 开头可见字符数
     * @param visibleEnd 末尾可见字符数
     */
    public MaskRule(MaskStrategy strategy, int visibleStart, int visibleEnd) {
        this(strategy, visibleStart, visibleEnd, '*');
    }

    /**
     * 默认构造函数（显示前3后4，掩码中间）
     */
    public MaskRule() {
        this(MaskStrategy.MIDDLE, 3, 4, '*');
    }

    @Override
    public String transform(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        int length = value.length();

        switch (strategy) {
            case MIDDLE:
                return maskMiddle(value, visibleStart, visibleEnd, maskChar);
            case START:
                return maskStart(value, visibleEnd, maskChar);
            case END:
                return maskEnd(value, visibleStart, maskChar);
            case FULL:
                return repeatChar(maskChar, length);
                // 其实在 JDK 11+ 中，可以使用如下方法
                //return String.valueOf(maskChar).repeat(length);
            case FIRST_LAST:
                return maskFirstLast(value, maskChar);
            default:
                return maskMiddle(value, 3, 4, maskChar);
        }
    }

    /**
     * 重复字符指定次数（JDK 8 兼容）
     *
     * @param c 要重复的字符
     * @param count 重复次数
     * @return 重复后的字符串
     */
    private String repeatChar(char c, int count) {
        if (count <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private String maskMiddle(String value, int startVisible, int endVisible, char maskChar) {
        int length = value.length();
        if (length <= startVisible + endVisible) {
            // 太短，不进行掩码
            return value;
        }

        StringBuilder masked = new StringBuilder();
        masked.append(value, 0, startVisible);

        for (int i = startVisible; i < length - endVisible; i++) {
            masked.append(maskChar);
        }

        masked.append(value.substring(length - endVisible));
        return masked.toString();
    }

    private String maskStart(String value, int endVisible, char maskChar) {
        int length = value.length();
        if (length <= endVisible) {
            return value;
        }

        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < length - endVisible; i++) {
            masked.append(maskChar);
        }
        masked.append(value.substring(length - endVisible));
        return masked.toString();
    }

    private String maskEnd(String value, int startVisible, char maskChar) {
        int length = value.length();
        if (length <= startVisible) {
            return value;
        }

        StringBuilder masked = new StringBuilder();
        masked.append(value.substring(0, startVisible));
        for (int i = startVisible; i < length; i++) {
            masked.append(maskChar);
        }
        return masked.toString();
    }

    private String maskFirstLast(String value, char maskChar) {
        int length = value.length();
        if (length <= 2) {
            return value;
        }

        StringBuilder masked = new StringBuilder();
        masked.append(value.charAt(0));
        for (int i = 1; i < length - 1; i++) {
            masked.append(maskChar);
        }
        if (length > 1) {
            masked.append(value.charAt(length - 1));
        }
        return masked.toString();
    }
}
