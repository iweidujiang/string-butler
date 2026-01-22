package io.github.iweidujiang.stringbutler.validation;

import io.github.iweidujiang.stringbutler.core.StringButler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 📦 验证规则测试
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class AdvancedValidationTest {
    @Test
    void testUrlValidation() {
        // 测试URL验证
        assertTrue(StringButler.of("https://github11.com/iweidujiang")
                .validate()
                .url()
                .validate()
                .isValid());

        assertTrue(StringButler.of("https://github66.com/iweidujiang")
                .validate()
                .url()
                .validate()
                .isValid());

        assertFalse(StringButler.of("not-a-url")
                .validate()
                .url()
                .validate()
                .isValid());
    }

    @Test
    void testPatternValidation() {
        // 测试正则表达式验证
        assertTrue(StringButler.of("123-456-7890")
                .validate()
                .matches("^\\d{3}-\\d{3}-\\d{4}$")
                .validate()
                .isValid());

        assertFalse(StringButler.of("123-456")
                .validate()
                .matches("^\\d{3}-\\d{3}-\\d{4}$")
                .validate()
                .isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "456789", "0"})
    void testIntegerValidation(String input) {
        // 测试整数验证
        assertTrue(StringButler.of(input)
                .validate()
                .integer()
                .validate()
                .isValid());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123.45", "0.1", "100.00"})
    void testDecimalValidation(String input) {
        // 测试小数验证
        assertTrue(StringButler.of(input)
                .validate()
                .decimal()
                .validate()
                .isValid());
    }

    @Test
    void testNumericValidation() {
        // 测试数字验证
        assertTrue(StringButler.of("123")
                .validate()
                .numeric(false)
                .validate()
                .isValid());

        assertTrue(StringButler.of("123.45")
                .validate()
                .numeric(true)
                .validate()
                .isValid());

        assertFalse(StringButler.of("123.45")
                .validate()
                .numeric(false) // 不允许小数
                .validate()
                .isValid());
    }
}
