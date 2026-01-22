package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.core.StringButler;
import io.github.iweidujiang.stringbutler.enums.MaskStrategy;
import io.github.iweidujiang.stringbutler.enums.TrimStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 📦 转换规则测试
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class AdvancedTransformationTest {

    @Test
    void testTrimStrategies() {
        // 测试各种trim策略
        assertEquals("hello  world  ",
                StringButler.of("  hello  world  ")
                        .transform()
                        .trim(TrimStrategy.START_ONLY)
                        .transform()
                        .getValue());

        assertEquals("  hello  world",
                StringButler.of("  hello  world  ")
                        .transform()
                        .trim(TrimStrategy.END_ONLY)
                        .transform()
                        .getValue());

        assertEquals("helloworld",
                StringButler.of("  hello  world  ")
                        .transform()
                        .trim(TrimStrategy.ALL_WHITESPACE)
                        .transform()
                        .getValue());

        assertEquals("hello world",
                StringButler.of("  hello   world  ")
                        .transform()
                        .trim(TrimStrategy.SMART)
                        .transform()
                        .getValue());
    }

    @Test
    void testTruncateTransformation() {
        // 测试截断转换
        assertEquals("Hello...",
                StringButler.of("Hello World!")
                        .transform()
                        .truncate(8)
                        .transform()
                        .getValue());

        assertEquals("Hello...",
                StringButler.of("Hello World!")
                        .transform()
                        .truncate(8, "...")
                        .transform()
                        .getValue());

        assertEquals("Hello World!",
                StringButler.of("Hello World!")
                        .transform()
                        .truncate(20) // 长度足够，不截断
                        .transform()
                        .getValue());
    }

    @Test
    void testSmartTruncate() {
        // 测试智能截断（保持单词完整性）
        assertEquals("Hello World...",
                StringButler.of("Hello World from Java")
                        .transform()
                        .truncate(15, "...", true)
                        .transform()
                        .getValue());

        assertEquals("Hello...",
                StringButler.of("HelloWorld")
                        .transform()
                        .truncate(8, "...", true)
                        .transform()
                        .getValue());
    }

    @Test
    void testMaskTransformation() {
        // 测试掩码转换
        assertEquals("123***7890",
                StringButler.of("1234567890")
                        .transform()
                        .mask()
                        .transform()
                        .getValue());

        assertEquals("******7890",
                StringButler.of("1234567890")
                        .transform()
                        .mask(MaskStrategy.START, 0, 4)
                        .transform()
                        .getValue());

        assertEquals("123456****",
                StringButler.of("1234567890")
                        .transform()
                        .mask(MaskStrategy.END, 6, 0)
                        .transform()
                        .getValue());

        assertEquals("**********",
                StringButler.of("1234567890")
                        .transform()
                        .mask(MaskStrategy.FULL, 0, 0)
                        .transform()
                        .getValue());

        assertEquals("1********0",
                StringButler.of("1234567890")
                        .transform()
                        .mask(MaskStrategy.FIRST_LAST, 1, 1)
                        .transform()
                        .getValue());
    }

    @Test
    void testRegexReplaceTransformation() {
        // 测试正则表达式替换
        assertEquals("XXX-XXX-XXXX",
                StringButler.of("123-456-7890")
                        .transform()
                        .regexReplace("\\d", "X")
                        .transform()
                        .getValue());
    }
}
