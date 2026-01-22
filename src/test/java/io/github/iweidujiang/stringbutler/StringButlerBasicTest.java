package io.github.iweidujiang.stringbutler;

import io.github.iweidujiang.stringbutler.core.StringButler;
import io.github.iweidujiang.stringbutler.enums.BlankStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 📦 StringButler 基础功能测试
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class StringButlerBasicTest {
    @Test
    void testFactoryMethod() {
        // 测试工厂方法
        StringButler butler = StringButler.of("test");
        assertNotNull(butler);
        assertEquals("test", butler.getValue());
    }

    @Test
    void testTrimOperation() {
        // 测试trim方法
        StringButler butler = StringButler.of("  hello world  ");
        butler.trim();

        assertEquals("hello world", butler.getValue());
        assertEquals("  hello world  ", butler.getOriginalValue());
    }

    @Test
    void testUpperCase() {
        // 测试大写转换
        StringButler butler = StringButler.of("hello");
        butler.toUpperCase();

        assertEquals("HELLO", butler.getValue());
    }

    @Test
    void testLowerCase() {
        // 测试小写转换
        StringButler butler = StringButler.of("HELLO");
        butler.toLowerCase();

        assertEquals("hello", butler.getValue());
    }

    @Test
    void testCapitalize() {
        // 测试首字母大写
        StringButler butler = StringButler.of("hello world");
        butler.capitalize();

        assertEquals("Hello world", butler.getValue());
    }

    @Test
    void testReplace() {
        // 测试替换操作
        StringButler butler = StringButler.of("hello world");
        butler.replace("world", "Java");

        assertEquals("hello Java", butler.getValue());
    }

    @Test
    void testChainedOperations() {
        // 测试链式操作
        String result = StringButler.of("  HELLO WORLD  ")
                .trim()
                .toLowerCase()
                .replace("world", "java")
                .capitalize()
                .getValue();

        assertEquals("Hello java", result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @EmptySource  // 测试空字符串 ""
    @NullSource // 测试null值
    void testIfBlankWithDefault(String input) {
        // 测试空白字符串处理 - 使用默认值
        String result = StringButler.of(input)
                .ifBlank("default", BlankStrategy.USE_DEFAULT)
                .getValue();

        assertEquals("default", result);
    }

    @Test
    void testIfBlankWithException() {
        // 测试空白字符串处理 - 抛出异常
        assertThrows(IllegalArgumentException.class, () -> {
            StringButler.of("")
                    .ifBlank("default", BlankStrategy.THROW_EXCEPTION)
                    .getValue();
        });
    }

    @Test
    void testIfBlankWithIgnore() {
        // 测试空白字符串处理 - 忽略
        String result = StringButler.of("")
                .ifBlank("default", BlankStrategy.IGNORE)
                .getValue();

        assertEquals("", result);
    }

    @Test
    void testGetValueOr() {
        // 测试getValueOr方法
        String result1 = StringButler.of("valid")
                .getValueOr("default");
        assertEquals("valid", result1);

        String result2 = StringButler.of("")
                .getValueOr("default");
        assertEquals("default", result2);
    }

    @Test
    void testGetValueOptional() {
        // 测试getValueOptional方法
        Optional<String> result1 = StringButler.of("valid")
                .getValueOptional();
        assertTrue(result1.isPresent());
        assertEquals("valid", result1.get());

        Optional<String> result2 = StringButler.of("")
                .getValueOptional();
        assertFalse(result2.isPresent());
    }

    @Test
    void testGetOrThrow() {
        // 测试getOrThrow方法
        String result = StringButler.of("valid")
                .getOrThrow(() -> new RuntimeException("Should not throw"));
        assertEquals("valid", result);

        assertThrows(RuntimeException.class, () -> {
            StringButler.of("")
                    .getOrThrow(() -> new RuntimeException("Expected exception"));
        });
    }

    @Test
    void testToString() {
        // 测试toString方法
        StringButler butler = StringButler.of("test");
        String str = butler.toString();
        assertTrue(str.contains("StringButler"));
        assertTrue(str.contains("test"));
    }
}
