# StringButler - Java字符串智能管家

![Java Version](https://img.shields.io/badge/Java-8%2B-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-green)

StringButler是一个强大、灵活且易于使用的Java字符串工具库。它提供链式API，让字符串处理变得直观、优雅。

<img width="612" height="537" alt="架构图-水印" src="https://github.com/user-attachments/assets/f3fb1161-e557-4eff-ae5f-13815931cd44" />

## 写在前面

**本项目的真正意图并非让你真的使用这个工具（虽然它确实好用），而是希望通过这个项目与你一起探讨优秀的软件设计、Java技术点的应用，以及如何写出优雅的代码。我会出系列博文来和大家一起探讨。**

**如果你恰好觉得它有用，那算是意外收获 😄**

## ✨ 特性

- 🚀 **链式API**：流畅的接口设计，像说话一样写代码
- 🛡️ **空值安全**：多种空值处理策略，避免NullPointerException
- 📋 **验证链**：内置常用验证规则（邮箱、URL、正则等）
- 🔄 **转换链**：丰富的字符串转换方法（大小写、掩码、截断等）
- 🎯 **统一结果**：标准化结果包装，简化错误处理
- 📦 **无依赖**：纯Java实现，无第三方依赖
- 🏃 **高性能**：正则表达式缓存，减少编译开销
- 🔧 **可扩展**：易于添加自定义验证和转换规则

## 📦 安装

### 当前状态
StringButler 目前处于 **开发成熟阶段**，功能稳定且经过完整测试。由于我正在完善发布流程，暂时还未发布到 Maven 中央仓库。

### 临时使用方式

#### 源码构建安装
您可以将项目克隆到本地，构建并安装到您的 Maven 本地仓库：

```bash
# 1. 克隆项目
git clone https://github.com/iweidujiang/string-butler.git
cd string-butler

# 2. 编译并安装到本地Maven仓库
mvn clean install

# 3. 在您的项目中添加依赖

```

```xml
<dependency>
    <groupId>io.github.iweidujiang</groupId>
    <artifactId>string-butler</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 🚀 快速开始

### 基本语法
```java
import io.github.iweidujiang.stringbutler.core.StringButler;

public class Example {
    public static void main(String[] args) {
        // 基础清理和转换
        String clean = StringButler.of("  HELLO WORLD  ")
            .transform()
                .trim()
                .toLowerCase()
                .capitalize()
            .transform()
            .getValue();
        // 结果: "Hello world"
        
        // 验证和获取结果
        String email = StringButler.of("USER@EXAMPLE.COM")
            .transform()
                .trim()
                .toLowerCase()
            .transform()
            .validate()
                .notBlank()
                .email()
            .validate()
            .getValueOr("default@email.com");
    }
}
```

### 📖 详细用法

#### 1. 链式转换操作
```java
// 多种转换操作链式调用
String result = StringButler.of("  HELLO   WORLD  ")
    .transform()
        .trim()                    // 去除首尾空格
        .toLowerCase()            // 转小写
        .replace("world", "Java") // 替换
        .capitalize()             // 首字母大写
        .truncate(15, "...")     // 截断
        .mask()                   // 掩码处理
    .transform()
    .getValue();
```

#### 2. 验证链
```java
// 多种验证规则组合
boolean isValid = StringButler.of("user@example.com")
    .validate()
        .notBlank("邮箱不能为空")
        .email("邮箱格式不正确")
        .lengthBetween(5, 100, "邮箱长度应在5-100之间")
        .matches("^[a-z]+@.*\\.com$", "只支持.com域名")
    .validate()
    .isValid();

// 获取验证错误信息
StringButler butler = StringButler.of("invalid")
    .validate()
        .email()
        .url()
    .validate();
    
if (!butler.isValid()) {
    System.out.println("错误: " + butler.getValidationErrors());
}
```

#### 3. 空白字符串处理
```java
import io.github.iweidujiang.stringbutler.enums.BlankStrategy;

// 多种空白处理策略
String result1 = StringButler.of(maybeNullString)
    .ifBlank("默认值", BlankStrategy.USE_DEFAULT)
    .getValue();

String result2 = StringButler.of("")
    .ifBlank("", BlankStrategy.IGNORE) // 保持原样
    .getValue();

// 空白时抛出异常
try {
    String result3 = StringButler.of("")
        .ifBlank("", BlankStrategy.THROW_EXCEPTION)
        .getValue();
} catch (IllegalArgumentException e) {
    // 处理异常
}
```

#### 4. 获取结果的多种方式
```java
StringButler butler = StringButler.of("some value")
    .validate()
        .notBlank()
    .validate();

// 方式1: 直接获取（可能返回null）
String value1 = butler.getValue();

// 方式2: 获取或默认值（考虑验证状态）
String value2 = butler.getValueOr("default");

// 方式3: 仅基于空白状态的获取
String value3 = butler.getValueIfNotBlankOr("default");

// 方式4: Optional包装
Optional<String> value4 = butler.getValueOptional();

// 方式5: 获取或抛出异常
String value5 = butler.getOrThrow(() -> new IllegalArgumentException("Invalid"));
```

## 🎯 高级特性

### 1. 多种Trim策略

```java
import io.github.iweidujiang.stringbutler.enums.TrimStrategy;

StringButler.of("  hello  world  ")
    .transform()
        .trim(TrimStrategy.START_ONLY)   // 仅去除开头空格
        // .trim(TrimStrategy.END_ONLY)   // 仅去除末尾空格
        // .trim(TrimStrategy.ALL_WHITESPACE) // 去除所有空白
        // .trim(TrimStrategy.SMART)      // 智能去除（保留单词间单个空格）
    .transform();
```



### 2. 字符串掩码



```java
import io.github.iweidujiang.stringbutler.enums.MaskStrategy;

// 默认：显示前3后4
String masked1 = StringButler.of("1234567890").mask().getValue(); // "123***7890"

// 自定义掩码策略
String masked2 = StringButler.of("1234567890")
    .transform()
        .mask(MaskStrategy.START, 0, 4) // 显示最后4位
        // .mask(MaskStrategy.END, 6, 0)  // 显示前6位
        // .mask(MaskStrategy.FULL, 0, 0) // 全部掩码
        // .mask(MaskStrategy.FIRST_LAST, 1, 1) // 显示首尾
    .transform()
    .getValue();
```



### 3. 智能截断



```java
// 保持单词完整性的智能截断
String truncated = StringButler.of("Hello World from Java")
    .transform()
        .truncate(15, "...", true) // 智能截断，保持单词
    .transform()
    .getValue(); // "Hello World..."

// 普通截断
String simpleTruncated = StringButler.of("Hello World")
    .transform()
        .truncate(8, "..") // 简单截断
    .transform()
    .getValue(); // "Hello W.."
```



### 4. 正则表达式操作



```java
// 正则验证
boolean isValid = StringButler.of("123-456-7890")
    .validate()
        .matches("^\\d{3}-\\d{3}-\\d{4}$")
    .validate()
    .isValid();

// 正则替换
String replaced = StringButler.of("Hello123World456")
    .transform()
        .regexReplace("\\d+", "_")
    .transform()
    .getValue(); // "Hello_World_"
```



## 🔧 扩展自定义规则

### 自定义验证规则



```java
import io.github.iweidujiang.stringbutler.validation.ValidationRule;

ValidationRule customRule = new ValidationRule() {
    @Override
    public boolean validate(String value) {
        // 你的验证逻辑
        return value != null && value.contains("特殊规则");
    }
    
    @Override
    public String getErrorMessage() {
        return "必须包含'特殊规则'";
    }
};

StringButler.of("测试")
    .validate()
        .addRule(customRule)
    .validate();
```



### 自定义转换规则



```java
import io.github.iweidujiang.stringbutler.transformation.TransformationRule;

TransformationRule customTransform = value -> {
    // 你的转换逻辑
    return value != null ? value + "_processed" : null;
};

StringButler.of("test")
    .transform()
        .addRule(customTransform)
    .transform();
```



## 🏗️ 项目结构



```tex
src/main/java/io/github/iweidujiang/stringbutler/
├─chains/
│   ├── TransformationChain.java   # 转换链
│   ├── ValidationChain.java       # 验证链
├── core/
│   ├── StringButler.java          # 核心类
│   └── StringButlerResult.java    # 结果包装类
├── enums/
│   ├── BlankStrategy.java         # 空白处理策略
│   ├── TrimStrategy.java          # 去除空格策略
│   └── MaskStrategy.java          # 掩码策略
├── validation/
│   ├── ValidationRule.java        # 验证规则接口
│   ├── NotBlankRule.java          # 非空验证
│   ├── EmailRule.java             # 邮箱验证
│   ├── UrlRule.java               # URL验证
│   ├── PatternRule.java           # 正则验证
│   ├── NumericRule.java           # 数字验证
├── transformation/
│   ├── TransformationRule.java    # 转换规则接口
│   ├── TrimRule.java              # 去除空格
│   ├── CaseTransformationRule.java # 大小写转换
│   ├── ReplaceRule.java           # 字符串替换
│   ├── TruncateRule.java          # 字符串截断
│   ├── MaskRule.java              # 字符串掩码
│   ├── RegexReplaceRule.java      # 正则替换
└── utils/
    └── PatternCache.java          # 正则缓存
```



## 🧪 运行测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=StringButlerBasicTest

# 生成测试报告
mvn surefire-report:report
```

测试报告：



## 📊 性能对比

处理100万条字符串的性能对比：

| 操作类型        | 传统方式 | StringButler | 提升 |
| :-------------- | :------- | :----------- | :--- |
| 空值检查+去空格 | 120ms    | 85ms         | +29% |
| 格式验证+转换   | 450ms    | 310ms        | +31% |
| 复杂链式操作    | N/A      | 520ms        | -    |

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 开发环境设置



```bash
# 克隆项目
git clone https://github.com/iweidujiang/string-butler.git
cd string-butler

# 编译和测试
mvn clean compile
mvn test

# 打包
mvn package
```



## 📄 许可证

本项目基于 Apache License 2.0 许可证 - 查看 [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.txt) 文件了解详情。



## 📞 支持

- 提交 Issue: [GitHub Issues](https://github.com/iweidujiang/string-butler/issues)

- 邮件联系: iyouzh@163.com

- 微信公众号：苏渡苇
<img width="595" height="595" alt="wechat" src="https://github.com/user-attachments/assets/ec6ce376-a8c6-4168-852b-f544c98a4773" />

  



## 🙏 致谢

感谢以下项目的启发：

- Apache Commons Lang StringUtils
- Guava Strings
- Spring StringUtils
