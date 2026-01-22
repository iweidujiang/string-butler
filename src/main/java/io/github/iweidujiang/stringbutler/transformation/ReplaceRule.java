package io.github.iweidujiang.stringbutler.transformation;

/**
 * 📦 替换字符串转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class ReplaceRule implements TransformationRule {
    private final String target;
    private final String replacement;

    /**
     * 构造函数
     *
     * @param target 目标字符串
     * @param replacement 替换字符串
     */
    public ReplaceRule(String target, String replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public String transform(String value) {
        if (value == null || target == null) {
            return value;
        }
        return value.replace(target, replacement);
    }
}
