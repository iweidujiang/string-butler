package io.github.iweidujiang.stringbutler.validation;

import java.util.regex.Pattern;

/**
 * 📦 数字验证规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class NumericRule implements ValidationRule {
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

    private final boolean allowDecimal;
    private final String errorMessage;

    /**
     * 构造函数
     *
     * @param allowDecimal 是否允许小数
     * @param errorMessage 错误信息
     */
    public NumericRule(boolean allowDecimal, String errorMessage) {
        this.allowDecimal = allowDecimal;
        this.errorMessage = errorMessage;
    }

    /**
     * 默认构造函数（不允许小数）
     */
    public NumericRule() {
        this(false, "String must be numeric");
    }

    /**
     * 构造函数（指定是否允许小数）
     *
     * @param allowDecimal 是否允许小数
     */
    public NumericRule(boolean allowDecimal) {
        this(allowDecimal, allowDecimal ?
                "String must be a valid number" : "String must be an integer");
    }

    @Override
    public boolean validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        if (allowDecimal) {
            return DECIMAL_PATTERN.matcher(value.trim()).matches();
        } else {
            return NUMERIC_PATTERN.matcher(value.trim()).matches();
        }
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
