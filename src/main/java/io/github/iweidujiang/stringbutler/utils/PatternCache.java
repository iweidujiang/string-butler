package io.github.iweidujiang.stringbutler.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 📦 正则表达式缓存工具类
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class PatternCache {
    private static final Map<String, Pattern> CACHE = new ConcurrentHashMap<>();
    private static final int MAX_CACHE_SIZE = 100;

    private PatternCache() {
        // 工具类，防止实例化
    }

    /**
     * 获取编译好的正则表达式模式
     *
     * @param regex 正则表达式字符串
     * @return 编译好的Pattern对象
     */
    public static Pattern getPattern(String regex) {
        return getPattern(regex, 0);
    }

    /**
     * 获取编译好的正则表达式模式
     *
     * @param regex 正则表达式字符串
     * @param flags 编译标志
     * @return 编译好的Pattern对象
     */
    public static Pattern getPattern(String regex, int flags) {
        if (regex == null) {
            throw new IllegalArgumentException("Regex cannot be null");
        }

        String key = regex + "|" + flags;

        // 使用双重检查确保线程安全
        Pattern pattern = CACHE.get(key);
        if (pattern == null) {
            synchronized (PatternCache.class) {
                pattern = CACHE.get(key);
                if (pattern == null) {
                    pattern = Pattern.compile(regex, flags);

                    // 限制缓存大小
                    if (CACHE.size() >= MAX_CACHE_SIZE) {
                        // 简单策略：移除第一个条目
                        String firstKey = CACHE.keySet().iterator().next();
                        CACHE.remove(firstKey);
                    }

                    CACHE.put(key, pattern);
                }
            }
        }

        return pattern;
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        CACHE.clear();
    }

    /**
     * 获取缓存大小
     *
     * @return 缓存中的条目数
     */
    public static int getCacheSize() {
        return CACHE.size();
    }
}
