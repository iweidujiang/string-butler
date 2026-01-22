package io.github.iweidujiang.stringbutler.transformation;

/**
 * 📦 大小写转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class CaseTransformationRule implements TransformationRule {
    public enum CaseType {
        UPPER,
        LOWER,
        CAPITALIZE
    }

    private final CaseType caseType;

    /**
     * 构造函数
     *
     * @param caseType 大小写类型
     */
    public CaseTransformationRule(CaseType caseType) {
        this.caseType = caseType;
    }

    @Override
    public String transform(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        switch (caseType) {
            case UPPER:
                return value.toUpperCase();
            case LOWER:
                return value.toLowerCase();
            case CAPITALIZE:
                return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
            default:
                return value;
        }
    }
}
