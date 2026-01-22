package io.github.iweidujiang.stringbutler.core;

import io.github.iweidujiang.stringbutler.enums.BlankStrategy;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 📦 StringButler - 字符串的智能管家
 * <p>提供链式、流畅的字符串操作API，简化常见的字符串处理任务。</p>
 *
 * <p>👤 作者：苏渡苇 </p>
 * <p>🔗 公众号：苏渡苇 </p>
 * <p> 💻 GitHub：https://github.com/iweidujiang </p>
 *
 * 📅 @date 2026/1/22
 */
public class StringButler {
    private final String originalValue;
    private String currentValue;
    private boolean isValid = true;
    private StringBuilder validationErrors = new StringBuilder();

    /**
     * 私有构造器，强制使用工厂方法
     *
     * @param value 要处理的字符串
     */
    private StringButler(String value) {
        this.originalValue = value;
        this.currentValue = value;
    }

    /**
     * 工厂方法：创建 StringButler 实例
     *
     * @param value 要处理的字符串，可以为null
     * @return StringButler实例
     */
    public static StringButler of(String value) {
        return new StringButler(value);
    }

    /**
     * 检查字符串是否为空白（null或仅包含空白字符）
     *
     * @param str 要检查的字符串
     * @return 如果为空白返回true，否则返回false
     */
    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 空白字符串处理
     *
     * @param defaultValue 默认值
     * @param strategy 处理策略
     * @return 当前实例（链式调用）
     */
    public StringButler ifBlank(String defaultValue, BlankStrategy strategy) {
        if (isBlank(currentValue)) {
            switch (strategy) {
                case USE_DEFAULT:
                    this.currentValue = defaultValue;
                    break;
                case THROW_EXCEPTION:
                    throw new IllegalArgumentException("String cannot be blank");
                case IGNORE:
                    // 保持原样，不做处理
                    break;
                case LOG_WARNING:
                    System.err.println("Warning: String is blank, keeping original value");
                    break;
            }
        }
        return this;
    }

    /**
     * 去除字符串首尾空白字符
     *
     * @return 当前实例
     */
    public StringButler trim() {
        if (currentValue != null) {
            this.currentValue = currentValue.trim();
        }
        return this;
    }

    /**
     * 转换为大写
     *
     * @return 当前实例
     */
    public StringButler toUpperCase() {
        if (currentValue != null) {
            this.currentValue = currentValue.toUpperCase();
        }
        return this;
    }

    /**
     * 转换为小写
     *
     * @return 当前实例
     */
    public StringButler toLowerCase() {
        if (currentValue != null) {
            this.currentValue = currentValue.toLowerCase();
        }
        return this;
    }

    /**
     * 首字母大写
     *
     * @return 当前实例
     */
    public StringButler capitalize() {
        if (currentValue != null && !currentValue.isEmpty()) {
            this.currentValue = currentValue.substring(0, 1).toUpperCase() +
                    currentValue.substring(1).toLowerCase();
        }
        return this;
    }

    /**
     * 替换字符串中的字符或子串
     *
     * @param target 目标字符串
     * @param replacement 替换字符串
     * @return 当前实例
     */
    public StringButler replace(String target, String replacement) {
        if (currentValue != null && target != null) {
            this.currentValue = currentValue.replace(target, replacement);
        }
        return this;
    }

    /**
     * 获取处理后的值
     *
     * @return 处理后的字符串，可能为null
     */
    public String getValue() {
        return currentValue;
    }

    /**
     * 获取原始值
     *
     * @return 原始字符串
     */
    public String getOriginalValue() {
        return originalValue;
    }

    /**
     * 获取值或默认值
     *
     * @param defaultValue 默认值
     * @return 如果当前值不为null且非空，则返回当前值，否则返回默认值
     */
    public String getValueOr(String defaultValue) {
        return (currentValue != null && !currentValue.trim().isEmpty()) ? currentValue : defaultValue;
    }

    /**
     * 获取 Optional 包装的值
     *
     * @return Optional包装的字符串
     */
    public Optional<String> getValueOptional() {
        return Optional.ofNullable(currentValue)
                .filter(s -> !s.trim().isEmpty());
    }

    /**
     * 获取值或抛出异常
     *
     * @param exceptionSupplier 异常提供者
     * @param <X> 异常类型
     * @return 处理后的字符串
     * @throws X 如果值为null或空字符串
     */
    public <X extends Throwable> String getOrThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isBlank(currentValue)) {
            throw exceptionSupplier.get();
        }
        return currentValue;
    }

    /**
     * 检查当前值是否有效（没有验证错误）
     *
     * @return 是否有效
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * 获取验证错误信息
     *
     * @return 错误信息
     */
    public String getValidationErrors() {
        return validationErrors.toString();
    }

    /**
     * 添加验证错误
     *
     * @param error 错误信息
     */
    void addValidationError(String error) {
        this.isValid = false;
        if (validationErrors.length() > 0) {
            validationErrors.append("; ");
        }
        validationErrors.append(error);
    }

    /**
     * 设置当前值（供链式类使用）
     *
     * @param value 新值
     */
    void setCurrentValue(String value) {
        this.currentValue = value;
    }

    @Override
    public String toString() {
        return String.format("StringButler{original='%s', current='%s', valid=%s}",
                originalValue, currentValue, isValid);
    }
}
