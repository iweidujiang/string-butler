package io.github.iweidujiang.stringbutler.transformation;

import io.github.iweidujiang.stringbutler.core.StringButler;
import io.github.iweidujiang.stringbutler.enums.MaskStrategy;
import io.github.iweidujiang.stringbutler.enums.TrimStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
     * 添加去除空格转换（使用指定策略）
     *
     * @param strategy 去除空格策略
     * @return 当前转换链实例
     */
    public TransformationChain trim(TrimStrategy strategy) {
        return addRule(new TrimRule(strategy));
    }

    /**
     * 添加去除空格转换（使用默认策略）
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
     * 添加字符串截断转换
     *
     * @param maxLength 最大长度
     * @return 当前转换链实例
     */
    public TransformationChain truncate(int maxLength) {
        return addRule(new TruncateRule(maxLength));
    }

    /**
     * 添加字符串截断转换（自定义后缀）
     *
     * @param maxLength 最大长度
     * @param suffix 截断后添加的后缀
     * @return 当前转换链实例
     */
    public TransformationChain truncate(int maxLength, String suffix) {
        return addRule(new TruncateRule(maxLength, suffix));
    }

    /**
     * 添加字符串截断转换（完整参数）
     *
     * @param maxLength 最大长度
     * @param suffix 截断后添加的后缀
     * @param preserveWords 是否保持单词完整性
     * @return 当前转换链实例
     */
    public TransformationChain truncate(int maxLength, String suffix, boolean preserveWords) {
        return addRule(new TruncateRule(maxLength, suffix, preserveWords));
    }

    /**
     * 添加字符串掩码转换
     *
     * @return 当前转换链实例
     */
    public TransformationChain mask() {
        return addRule(new MaskRule());
    }

    /**
     * 添加字符串掩码转换（指定策略）
     *
     * @param strategy 掩码策略
     * @param visibleStart 开头可见字符数
     * @param visibleEnd 末尾可见字符数
     * @return 当前转换链实例
     */
    public TransformationChain mask(MaskStrategy strategy, int visibleStart, int visibleEnd) {
        return addRule(new MaskRule(strategy, visibleStart, visibleEnd));
    }

    /**
     * 添加字符串掩码转换（完整参数）
     *
     * @param strategy 掩码策略
     * @param visibleStart 开头可见字符数
     * @param visibleEnd 末尾可见字符数
     * @param maskChar 掩码字符
     * @return 当前转换链实例
     */
    public TransformationChain mask(MaskStrategy strategy, int visibleStart, int visibleEnd, char maskChar) {
        return addRule(new MaskRule(strategy, visibleStart, visibleEnd, maskChar));
    }

    /**
     * 添加正则表达式替换转换
     *
     * @param pattern 正则表达式
     * @param replacement 替换字符串
     * @return 当前转换链实例
     */
    public TransformationChain regexReplace(String pattern, String replacement) {
        return addRule(new RegexReplaceRule(pattern, replacement));
    }

    /**
     * 添加正则表达式替换转换（使用预编译的Pattern）
     *
     * @param pattern 预编译的正则表达式
     * @param replacement 替换字符串
     * @return 当前转换链实例
     */
    public TransformationChain regexReplace(Pattern pattern, String replacement) {
        return addRule(new RegexReplaceRule(pattern, replacement));
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
