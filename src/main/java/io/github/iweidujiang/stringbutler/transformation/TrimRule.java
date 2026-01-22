package io.github.iweidujiang.stringbutler.transformation;

/**
 * 📦 去除空格转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class TrimRule implements TransformationRule {
    @Override
    public String transform(String value) {
        return value != null ? value.trim() : null;
    }
}
