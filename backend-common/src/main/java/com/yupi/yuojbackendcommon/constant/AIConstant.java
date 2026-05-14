package com.yupi.yuojbackendcommon.constant;

/**
 * @Description: ai的常量
 * @Author: Yhj
 * @Date: 2025/2/5 20:26
 */
public interface AIConstant {
    /**
     * 自动创建题目
     */
    String AUTO_CREATE_QUESTION = "你是一位严谨的出题专家，我会给你如下信息：\n" +
            "```\n" +
            "题目名称：\n" +
            "```\n" +
            "请你根据上述信息，按照以下步骤来出题：\n" +
            "题目标题，题目标签，题目内容、题目答案及说明，判题规则（其中包含题目内存占用情况，堆栈占用情况，时间限制），测试用例，请你根据上述信息，按照以下步骤来出题：1.严格按照下面的 json 格式输出\n" +
            "{\n" +
            "  title: \"\",\n" +
            "  tags: [],\n" +
            "  answer: \"\",\n" +
            "  content: \"\",\n" +
            "  judgeConfig: {\n" +
            "    memoryLimit: 1000,\n" +
            "    stackLimit: 1000,\n" +
            "    timeLimit: 1000,\n" +
            "  },\n" +
            "  judgeCase: [\n" +
            "    {\n" +
            "      input: \"\",\n" +
            "      output: \"\",\n" +
            "    },\n" +
            "  ],\n" +
            "}title是题目标题，tags是题目标签，answer是题目答案及说明，judgeConfig是判题规则，judgeCase为测试用例。2.返回的题目列表格式必须为一个 JSON 格式。3.输入输出用例可以为任意多个。4.答案必须为编程语言一种（最好是Java），并能解决该问题。5.只需返回一个json数据即可，不要数组。题目如果没给告诉你，可以随机生成6.memoryLimit和stackLimit单位为B，timeLimit单位为ms。最后，一定注意第四点，给出编程语言写的函数后再给出答案说明，并且content和answer可以使用markdown语法\n"
            +"7.题目输入输出示例为：input: \"1 1\",output: \"2\",只以空格分割，不要有其他分隔符，例如，"+"8.不要加任何注释注意尤其是judgeConfig，确保answer和content特殊字符正确转义为一个字符串。";

}
