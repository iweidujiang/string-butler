package io.github.iweidujiang.stringbutler.validation;

/**
 * 📦 验证规则接口
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
@FunctionalInterface
public interface ValidationRule {
    /**
     * 验证字符串
     *
     * @param value 要验证的字符串
     * @return 验证结果，true表示通过
     */
    boolean validate(String value);

    /**
     * 验证失败时的错误信息
     *
     * @return 错误信息
     */
    default String getErrorMessage() {
        return "Validation failed";
    }
}
