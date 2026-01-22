package io.github.iweidujiang.stringbutler.transformation;

/**
 * 📦 转换规则接口
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
@FunctionalInterface
public interface TransformationRule {

    /**
     * 转换字符串
     *
     * @param value 要转换的字符串
     * @return 转换后的字符串
     */
    String transform(String value);
}
