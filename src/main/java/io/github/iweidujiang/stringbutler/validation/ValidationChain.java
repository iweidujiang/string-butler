package io.github.iweidujiang.stringbutler.validation;

import io.github.iweidujiang.stringbutler.core.StringButler;

import java.util.ArrayList;
import java.util.List;

/**
 * 📦 验证链，用于执行多个验证规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class ValidationChain {
    private final StringButler butler;
    private final List<ValidationRule> rules = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();
    private boolean stopOnFirstFailure = false;

    /**
     * 构造函数
     *
     * @param butler 关联的StringButler实例
     */
    public ValidationChain(StringButler butler) {
        this.butler = butler;
    }

    /**
     * 添加验证规则
     *
     * @param rule 验证规则
     * @return 当前验证链实例
     */
    public ValidationChain addRule(ValidationRule rule) {
        rules.add(rule);
        return this;
    }

    /**
     * 添加非空验证
     *
     * @return 当前验证链实例
     */
    public ValidationChain notBlank() {
        return addRule(new NotBlankRule());
    }

    /**
     * 添加非空验证（自定义错误信息）
     *
     * @param errorMessage 错误信息
     * @return 当前验证链实例
     */
    public ValidationChain notBlank(String errorMessage) {
        return addRule(new NotBlankRule(errorMessage));
    }

    /**
     * 添加邮箱格式验证
     *
     * @return 当前验证链实例
     */
    public ValidationChain email() {
        return addRule(new EmailRule());
    }

    /**
     * 添加邮箱格式验证（自定义错误信息）
     *
     * @param errorMessage 错误信息
     * @return 当前验证链实例
     */
    public ValidationChain email(String errorMessage) {
        return addRule(new EmailRule(errorMessage));
    }

    /**
     * 添加长度验证
     *
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 当前验证链实例
     */
    public ValidationChain lengthBetween(int minLength, int maxLength) {
        return addRule(new LengthRule(minLength, maxLength));
    }

    /**
     * 添加长度验证（自定义错误信息）
     *
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @param errorMessage 错误信息
     * @return 当前验证链实例
     */
    public ValidationChain lengthBetween(int minLength, int maxLength, String errorMessage) {
        return addRule(new LengthRule(minLength, maxLength, errorMessage));
    }

    /**
     * 添加URL格式验证
     *
     * @return 当前验证链实例
     */
    public ValidationChain url() {
        return addRule(new UrlRule());
    }

    /**
     * 添加URL格式验证（自定义错误信息）
     *
     * @param errorMessage 错误信息
     * @return 当前验证链实例
     */
    public ValidationChain url(String errorMessage) {
        return addRule(new UrlRule(errorMessage));
    }

    /**
     * 添加正则表达式验证
     *
     * @param pattern 正则表达式
     * @return 当前验证链实例
     */
    public ValidationChain matches(String pattern) {
        return addRule(new PatternRule(pattern));
    }

    /**
     * 添加正则表达式验证（自定义错误信息）
     *
     * @param pattern 正则表达式
     * @param errorMessage 错误信息
     * @return 当前验证链实例
     */
    public ValidationChain matches(String pattern, String errorMessage) {
        return addRule(new PatternRule(pattern, errorMessage));
    }

    /**
     * 添加数字验证
     *
     * @param allowDecimal 是否允许小数
     * @return 当前验证链实例
     */
    public ValidationChain numeric(boolean allowDecimal) {
        return addRule(new NumericRule(allowDecimal));
    }

    /**
     * 添加数字验证（自定义错误信息）
     *
     * @param allowDecimal 是否允许小数
     * @param errorMessage 错误信息
     * @return 当前验证链实例
     */
    public ValidationChain numeric(boolean allowDecimal, String errorMessage) {
        return addRule(new NumericRule(allowDecimal, errorMessage));
    }

    /**
     * 添加整数验证
     *
     * @return 当前验证链实例
     */
    public ValidationChain integer() {
        return numeric(false);
    }

    /**
     * 添加小数验证
     *
     * @return 当前验证链实例
     */
    public ValidationChain decimal() {
        return numeric(true);
    }

    /**
     * 设置是否在第一次验证失败时停止
     *
     * @param stopOnFirstFailure 是否在第一次失败时停止
     * @return 当前验证链实例
     */
    public ValidationChain stopOnFirstFailure(boolean stopOnFirstFailure) {
        this.stopOnFirstFailure = stopOnFirstFailure;
        return this;
    }

    /**
     * 执行所有验证
     *
     * @return 关联的StringButler实例
     */
    public StringButler validate() {
        String value = butler.getValue();
        boolean allValid = true;
        for (ValidationRule rule : rules) {
            boolean isValid = rule.validate(value);
            if (!isValid) {
                allValid = false;
                errors.add(rule.getErrorMessage());
                butler.addValidationError(rule.getErrorMessage());
                if (stopOnFirstFailure) {
                    break;
                }
            }
        }
        return butler;
    }

    /**
     * 获取验证错误列表
     *
     * @return 错误列表
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    /**
     * 检查是否所有验证都通过
     *
     * @return 是否全部通过
     */
    public boolean isValid() {
        return errors.isEmpty();
    }
}
