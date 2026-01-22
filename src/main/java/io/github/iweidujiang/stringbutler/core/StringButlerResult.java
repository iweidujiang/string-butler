package io.github.iweidujiang.stringbutler.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 📦 StringButler 的结果包装类
 *
 * <p>封装 StringButler 处理结果，包含值、状态和错误信息</p>
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class StringButlerResult {

    private final String value;
    private final boolean valid;
    private final List<String> errors;
    private final long processingTime;

    /**
     * 构造函数
     *
     * @param value 结果值
     * @param valid 是否有效
     * @param errors 错误列表
     */
    public StringButlerResult(String value, boolean valid, List<String> errors) {
        this.value = value;
        this.valid = valid;
        this.errors = errors != null ?
                Collections.unmodifiableList(new ArrayList<>(errors)) :
                Collections.emptyList();
        this.processingTime = System.currentTimeMillis();
    }

    /**
     * 创建成功结果
     *
     * @param value 结果值
     * @return StringButlerResult 实例
     */
    public static StringButlerResult success(String value) {
        return new StringButlerResult(value, true, null);
    }

    /**
     * 创建失败结果
     *
     * @param error 错误信息
     * @return ButlerResult实例
     */
    public static StringButlerResult failure(String error) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        return new StringButlerResult(null, false, errors);
    }

    /**
     * 创建失败结果（多个错误）
     *
     * @param errors 错误列表
     * @return ButlerResult实例
     */
    public static StringButlerResult failure(List<String> errors) {
        return new StringButlerResult(null, false, errors);
    }

    /**
     * 获取结果值
     *
     * @return 结果值，可能为null
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取值或默认值
     *
     * @param defaultValue 默认值
     * @return 如果结果有效且值不为null，则返回值，否则返回默认值
     */
    public String getValueOr(String defaultValue) {
        return valid && value != null ? value : defaultValue;
    }

    /**
     * 获取值或默认值（仅基于null状态，不考虑验证状态）
     *
     * @param defaultValue 默认值
     * @return 如果值不为null，则返回值，否则返回默认值
     */
    public String getValueIfNotNullOr(String defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 获取 Optional 包装的值
     *
     * @return Optional包装的字符串
     */
    public Optional<String> getValueOptional() {
        return valid && value != null ? Optional.of(value) : Optional.empty();
    }

    /**
     * 获取值或抛出异常
     *
     * @param exceptionSupplier 异常提供者
     * @param <X> 异常类型
     * @return 结果值
     * @throws X 如果结果无效或值为null
     */
    public <X extends Throwable> String getOrThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (!valid) {
            throw exceptionSupplier.get();
        }
        if (value == null) {
            throw exceptionSupplier.get();
        }
        return value;
    }

    /**
     * 检查结果是否有效
     *
     * @return 是否有效
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * 获取错误列表
     *
     * @return 不可修改的错误列表
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * 检查是否有错误
     *
     * @return 是否有错误
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * 获取处理时间
     *
     * @return 处理时间（毫秒）
     */
    public long getProcessingTime() {
        return processingTime;
    }

    @Override
    public String toString() {
        return String.format("ButlerResult{valid=%s, value='%s', errors=%s, processingTime=%d}",
                valid, value, errors, processingTime);
    }
}
