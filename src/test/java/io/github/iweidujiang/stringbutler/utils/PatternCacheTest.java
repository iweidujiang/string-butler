package io.github.iweidujiang.stringbutler.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 📦 测试缓存机制
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class PatternCacheTest {
    @Test
    void testPatternCache() {
        // 清空缓存以便测试
        PatternCache.clearCache();

        // 第一次获取，应该编译并缓存
        Pattern pattern1 = PatternCache.getPattern("\\d+");
        assertNotNull(pattern1);
        assertEquals(1, PatternCache.getCacheSize());

        // 第二次获取相同的模式，应该从缓存获取
        Pattern pattern2 = PatternCache.getPattern("\\d+");
        assertSame(pattern1, pattern2);
        assertEquals(1, PatternCache.getCacheSize());

        // 获取不同的模式，应该添加到缓存
        Pattern pattern3 = PatternCache.getPattern("\\w+");
        assertNotNull(pattern3);
        assertEquals(2, PatternCache.getCacheSize());

        // 获取带标志的模式，应该作为不同的键缓存
        Pattern pattern4 = PatternCache.getPattern("\\d+", Pattern.CASE_INSENSITIVE);
        assertNotNull(pattern4);
        assertEquals(3, PatternCache.getCacheSize());
    }

    @Test
    void testPatternCacheThreadSafety() throws InterruptedException {
        // 测试线程安全性
        PatternCache.clearCache();

        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                Pattern pattern = PatternCache.getPattern("test\\d+");
                assertNotNull(pattern);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // 应该只有1个缓存条目
        assertEquals(1, PatternCache.getCacheSize());
    }
}
