package io.github.iweidujiang.stringbutler.validation;

/**
 * 📦 长度验证规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class LengthRule implements ValidationRule {

    private final int minLength;
    private final int maxLength;
    private final String errorMessage;

    /**
     * 构造函数
     *
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @param errorMessage 错误信息
     */
    public LengthRule(int minLength, int maxLength, String errorMessage) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.errorMessage = errorMessage;
    }

    /**
     * 简化构造函数
     *
     * @param minLength 最小长度
     * @param maxLength 最大长度
     */
    public LengthRule(int minLength, int maxLength) {
        this(minLength, maxLength,
                String.format("Length must be between %d and %d characters", minLength, maxLength));
    }

    /**
     * 仅最小长度构造函数
     *
     * @param minLength 最小长度
     */
    public LengthRule(int minLength) {
        this(minLength, Integer.MAX_VALUE,
                String.format("Length must be at least %d characters", minLength));
    }

    @Override
    public boolean validate(String value) {
        if (value == null) {
            // null值的处理逻辑
            return minLength <= 0;
        }
        int length = value.length();
        return length >= minLength && length <= maxLength;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
