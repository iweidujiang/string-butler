package io.github.iweidujiang.stringbutler.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class StringButlerChainTest {
    @Test
    void testCompleteWorkflow() {
        // 测试完整的工作流程：转换 -> 验证 -> 获取结果
        String result = StringButler.of("  USER@EXAMPLE.COM  ")
                .transform()
                .trim()
                .toLowerCase()
                .replace("example.com", "gmail.com")
                .transform()
                .validate()
                .notBlank()
                .email()
                .lengthBetween(5, 50)
                .validate()
                .getValueOr("default@gmail.com");

        assertEquals("user@gmail.com", result);
    }

    @Test
    void testComplexChainedOperations() {
        // 测试复杂的链式操作
        StringButler butler = StringButler.of("  John Doe  ")
                .transform()
                .trim()
                .replace(" ", "_")
                .toLowerCase()
                .transform()
                .validate()
                .notBlank("Username is required")
                .lengthBetween(3, 20, "Username must be 3-20 characters")
                .validate();

        assertTrue(butler.isValid());
        assertEquals("john_doe", butler.getValue());
    }

    @Test
    void testInvalidWorkflowWithFallback() {
        // 测试无效工作流程并回退到默认值
        String result = StringButler.of("invalid-email")
                .transform()
                .trim()
                .toLowerCase()
                .transform()
                .validate()
                .email()
                .validate()
                .getValueOr("default@example.com");

        assertEquals("default@example.com", result);
    }
}
