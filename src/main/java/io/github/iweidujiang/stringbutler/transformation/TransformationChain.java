package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.core.StringButler;

import java.util.ArrayList;
import java.util.List;

/**
 * 📦 转换链，用于执行多个转换规则
 * <p>
 * 👤 作者：苏渡苇
 * <p>
 * 🔗 公众号：苏渡苇
 * <p>
 * 💻 GitHub：https://github.com/iweidujiang
 * <p>
 * 📅 @date 2026/1/22
 */
public class TransformationChain {
    private final StringButler butler;
    private final List<TransformationRule> rules = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param butler 关联的StringButler实例
     */
    public TransformationChain(StringButler butler) {
        this.butler = butler;
    }

    /**
     * 添加转换规则
     *
     * @param rule 转换规则
     * @return 当前转换链实例
     */
    public TransformationChain addRule(TransformationRule rule) {
        rules.add(rule);
        return this;
    }

    /**
     * 添加去除空格转换
     *
     * @return 当前转换链实例
     */
    public TransformationChain trim() {
        return addRule(new TrimRule());
    }

    /**
     * 添加大写转换
     *
     * @return 当前转换链实例
     */
    public TransformationChain toUpperCase() {
        return addRule(new CaseTransformationRule(CaseTransformationRule.CaseType.UPPER));
    }

    /**
     * 添加小写转换
     *
     * @return 当前转换链实例
     */
    public TransformationChain toLowerCase() {
        return addRule(new CaseTransformationRule(CaseTransformationRule.CaseType.LOWER));
    }

    /**
     * 添加首字母大写转换
     *
     * @return 当前转换链实例
     */
    public TransformationChain capitalize() {
        return addRule(new CaseTransformationRule(CaseTransformationRule.CaseType.CAPITALIZE));
    }

    /**
     * 添加字符串替换转换
     *
     * @param target 目标字符串
     * @param replacement 替换字符串
     * @return 当前转换链实例
     */
    public TransformationChain replace(String target, String replacement) {
        return addRule(new ReplaceRule(target, replacement));
    }

    /**
     * 执行所有转换
     *
     * @return 关联的StringButler实例
     */
    public StringButler transform() {
        String currentValue = butler.getValue();

        for (TransformationRule rule : rules) {
            currentValue = rule.transform(currentValue);
        }

        butler.setCurrentValue(currentValue);
        return butler;
    }

    /**
     * 获取转换规则数量
     *
     * @return 规则数量
     */
    public int getRuleCount() {
        return rules.size();
    }
}
