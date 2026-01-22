package io.github.iweidujiang.stringbutler.validation;

import io.github.iweidujiang.stringbutler.chains.ValidationChain;
import io.github.iweidujiang.stringbutler.core.StringButler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 📦 测试链式操作
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class ValidationChainTest {
    @Test
    void testNotBlankValidation() {
        // 测试非空验证
        StringButler butler = StringButler.of("valid")
                .validate()
                .notBlank()
                .validate();

        assertTrue(butler.isValid());

        StringButler invalidButler = StringButler.of("")
                .validate()
                .notBlank()
                .validate();

        assertFalse(invalidButler.isValid());
        assertTrue(invalidButler.getValidationErrors().contains("cannot be blank"));
    }

    @Test
    void testEmailValidation() {
        // 测试邮箱验证
        StringButler validEmail = StringButler.of("test@example.com")
                .validate()
                .email()
                .validate();

        assertTrue(validEmail.isValid());

        StringButler invalidEmail = StringButler.of("invalid-email")
                .validate()
                .email()
                .validate();

        assertFalse(invalidEmail.isValid());
    }

    @Test
    void testLengthValidation() {
        // 测试长度验证
        StringButler validLength = StringButler.of("12345")
                .validate()
                .lengthBetween(3, 10)
                .validate();

        assertTrue(validLength.isValid());

        StringButler tooShort = StringButler.of("12")
                .validate()
                .lengthBetween(3, 10)
                .validate();

        assertFalse(tooShort.isValid());
    }

    @Test
    void testMultipleValidations() {
        // 测试多个验证规则
        StringButler butler = StringButler.of("valid@example.com")
                .validate()
                .notBlank()
                .email()
                .lengthBetween(5, 50)
                .validate();

        assertTrue(butler.isValid());
    }

    @Test
    void testValidationWithCustomErrorMessage() {
        // 测试自定义错误信息
        StringButler butler = StringButler.of("")
                .validate()
                .notBlank("Custom error: field is required")
                .validate();

        assertFalse(butler.isValid());
        assertTrue(butler.getValidationErrors().contains("Custom error"));
    }

    @Test
    void testStopOnFirstFailure() {
        // 测试在第一次失败时停止
        ValidationChain chain = StringButler.of("")
                .validate()
                .notBlank()
                .email()
                .lengthBetween(5, 10);

        chain.stopOnFirstFailure(true);
        StringButler butler = chain.validate();

        assertFalse(butler.isValid());
        // 因为第一次失败就停止了，所以应该只有一个错误
        assertTrue(butler.getValidationErrors().split(";").length <= 1);
    }
}
