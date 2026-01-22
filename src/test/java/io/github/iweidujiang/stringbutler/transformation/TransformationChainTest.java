package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.core.StringButler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 📦 测试转换链
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class TransformationChainTest {
    @Test
    void testTrimTransformation() {
        // 测试去除空格
        String result = StringButler.of("  hello  ")
                .transform()
                .trim()
                .transform()
                .getValue();

        assertEquals("hello", result);
    }

    @Test
    void testCaseTransformation() {
        // 测试大小写转换
        String result1 = StringButler.of("hello")
                .transform()
                .toUpperCase()
                .transform()
                .getValue();
        assertEquals("HELLO", result1);

        String result2 = StringButler.of("WORLD")
                .transform()
                .toLowerCase()
                .transform()
                .getValue();
        assertEquals("world", result2);

        String result3 = StringButler.of("hello world")
                .transform()
                .capitalize()
                .transform()
                .getValue();
        assertEquals("Hello world", result3);
    }

    @Test
    void testReplaceTransformation() {
        // 测试字符串替换
        String result = StringButler.of("hello world")
                .transform()
                .replace("world", "Java")
                .transform()
                .getValue();

        assertEquals("hello Java", result);
    }

    @Test
    void testMultipleTransformations() {
        // 测试多个转换操作
        String result = StringButler.of("  HELLO WORLD  ")
                .transform()
                .trim()
                .toLowerCase()
                .replace("world", "java")
                .capitalize()
                .transform()
                .getValue();

        assertEquals("Hello java", result);
    }

    @Test
    void testTransformationChainCount() {
        // 测试转换规则数量
        TransformationChain chain = StringButler.of("test")
                .transform()
                .trim()
                .toUpperCase()
                .replace("TEST", "RESULT");

        assertEquals(3, chain.getRuleCount());
    }
}
