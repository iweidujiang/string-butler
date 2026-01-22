package io.github.iweidujiang.stringbutler.validation;

/**
 * 📦 非空验证规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class NotBlankRule implements ValidationRule {

    private final String errorMessage;

    /**
     * 构造函数
     *
     * @param errorMessage 错误信息
     */
    public NotBlankRule(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 默认构造函数
     */
    public NotBlankRule() {
        this("String cannot be blank");
    }

    @Override
    public boolean validate(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
