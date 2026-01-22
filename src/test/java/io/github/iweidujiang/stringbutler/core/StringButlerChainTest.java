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
        String result = StringButler.of("  IYOUZh@GITHUB.COM  ")
                .transform()
                .trim()
                .toLowerCase()
                .replace("github.com", "163.com")
                .transform()
                .validate()
                .notBlank()
                .email()
                .lengthBetween(5, 50)
                .validate()
                .getValueOr("iyouzh@163.com");

        assertEquals("iyouzh@163.com", result);
    }

    @Test
    void testComplexChainedOperations() {
        // 测试复杂的链式操作
        StringButler butler = StringButler.of("  苏渡苇 IweiduJiang  ")
                .transform()
                .trim()
                .replace(" ", "-")
                .toLowerCase()
                .transform()
                .validate()
                .notBlank("Username is required")
                .lengthBetween(3, 20, "Username must be 3-20 characters")
                .validate();

        assertTrue(butler.isValid());
        assertEquals("苏渡苇-iweidujiang", butler.getValue());
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
                .getValueOr("iyouzh@163.com");

        // 现在验证失败应该返回默认值
        assertEquals("iyouzh@163.com", result);

        // 测试新的getValueIfNotBlankOr方法
        String result2 = StringButler.of("invalid-email")
                .transform()
                .trim()
                .toLowerCase()
                .transform()
                .validate()
                .email()
                .validate()
                .getValueIfNotBlankOr("iweidujiang@github.com");

        // getValueIfNotBlankOr应该返回原值（因为不检查验证状态）
        assertEquals("invalid-email", result2);
    }

    @Test
    void testValidWorkflowWithGetValueOr() {
        // 测试有效工作流程应该返回处理后的值
        String result = StringButler.of("  iyouzh@163.com  ")
                .transform()
                .trim()
                .toLowerCase()
                .transform()
                .validate()
                .email()
                .validate()
                .getValueOr("iyouzh@163.com");

        // 验证通过，应该返回处理后的值
        assertEquals("iyouzh@163.com", result);
    }
}
