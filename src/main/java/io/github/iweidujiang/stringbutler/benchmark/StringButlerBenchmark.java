package io.github.iweidujiang.stringbutler.benchmark;

import io.github.iweidujiang.stringbutler.core.StringButler;
import io.github.iweidujiang.stringbutler.enums.TrimStrategy;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * 📦 性能测试
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
public class StringButlerBenchmark {
    // 测试数据：模拟真实场景中的字符串
    private static final String TEST_STRING = "  Hello World! This is a test string.  ";
    private static final String[] TEST_STRINGS = new String[1_000_000];

    @Setup
    public void setup() {
        // 准备100万个测试字符串
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            TEST_STRINGS[i] = TEST_STRING + " #" + i;
        }
    }

    // 基准测试1：传统方式（Java原生链式）
    @Benchmark
    public void testTraditionalChain(Blackhole bh) {
        for (String str : TEST_STRINGS) {
            String result = str
                    .trim()
                    .toLowerCase()
                    .replace("world", "java")
                    .replace("test", "benchmark")
                    .substring(0, Math.min(20, str.length()));
            bh.consume(result);
        }
    }

    // 基准测试2：StringButler方式（基本链式）
    @Benchmark
    public void testStringButlerBasic(Blackhole bh) {
        for (String str : TEST_STRINGS) {
            String result = StringButler.of(str)
                    .transform()
                    .trim()
                    .toLowerCase()
                    .replace("world", "java")
                    .replace("test", "benchmark")
                    .truncate(20)  // 使用truncate替代substring
                    .transform()
                    .getValue();
            bh.consume(result);
        }
    }

    // 基准测试3：StringButler方式（带验证的完整链式）
    @Benchmark
    public void testStringButlerFull(Blackhole bh) {
        for (String str : TEST_STRINGS) {
            String result = StringButler.of(str)
                    .transform()
                    .trim()
                    .toLowerCase()
                    .replace("world", "java")
                    .transform()
                    .validate()
                    .notBlank()
                    .lengthBetween(5, 1000)
                    .matches(".*java.*")
                    .validate()
                    .getValueOr("default");
            bh.consume(result);
        }
    }

    // 基准测试4：Apache Commons Lang方式（对比）
    @Benchmark
    public void testApacheCommons(Blackhole bh) {
        for (String str : TEST_STRINGS) {
            String trimmed = org.apache.commons.lang3.StringUtils.trim(str);
            String lower = org.apache.commons.lang3.StringUtils.lowerCase(trimmed);
            String replaced1 = org.apache.commons.lang3.StringUtils.replace(lower, "world", "java");
            String replaced2 = org.apache.commons.lang3.StringUtils.replace(replaced1, "test", "benchmark");
            String result = org.apache.commons.lang3.StringUtils.substring(replaced2, 0, 20);
            bh.consume(result);
        }
    }

    // 基准测试5：传统if-else方式（作为对比）
    @Benchmark
    public void testTraditionalIfElse(Blackhole bh) {
        for (String str : TEST_STRINGS) {
            String result;
            if (str == null) {
                result = "default";
            } else {
                String trimmed = str.trim();
                if (trimmed.isEmpty()) {
                    result = "default";
                } else {
                    String lower = trimmed.toLowerCase();
                    String replaced1 = lower.replace("world", "java");
                    String replaced2 = replaced1.replace("test", "benchmark");
                    if (replaced2.length() > 20) {
                        result = replaced2.substring(0, 20);
                    } else {
                        result = replaced2;
                    }
                }
            }
            bh.consume(result);
        }
    }

    // 基准测试6：StringButler复杂操作（多种trim策略、掩码等）
    @Benchmark
    public void testStringButlerComplex(Blackhole bh) {
        for (String str : TEST_STRINGS) {
            String result = StringButler.of(str)
                    .transform()
                    .trim(TrimStrategy.SMART)  // 智能trim
                    .toLowerCase()
                    .replace("world", "java")
                    .mask()  // 默认掩码：显示前3后4
                    .truncate(25, "...", true)  // 智能截断
                    .transform()
                    .validate()
                    .notBlank("字符串不能为空")
                    .lengthBetween(10, 100, "长度应在10-100之间")
                    .validate()
                    .getValueOr("处理失败");
            bh.consume(result);
        }
    }
}
