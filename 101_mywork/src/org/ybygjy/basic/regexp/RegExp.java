package org.ybygjy.basic.regexp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author WangYanCheng
 * @version 2010-1-26
 */
public class RegExp {
    /**
     * 匹配文件部分内容
     * @param srcStr srcStr
     */
    public void doMatchFileContent() {
        String[] tmpStr = {"@depJs org.ybygjy\n@depJs org.ybygjy.j2ee\n@depJs org.ybygjy.j2se"};
        Pattern nsPattInst = Pattern.compile("@depJs\\s+((\\w+\\.*)+\\w+)\\r?\\n?");
        for (String srcStr : tmpStr) {
            Matcher matcherInst = nsPattInst.matcher(srcStr);
            while (matcherInst.find()) {
                System.out.println(matcherInst.group(1));
            }
        }
    }

    /**
     * 提取名称空间
     */
    public void doMatchNamespace() {
        String[] nsArr = new String[]{"Nstc.ibs.InnerQryRightAccountCombo", "Nstc.ibs.ChannelCombo", "Nstc.ibs.CustomerCombo", "Nstc.ibs.NetBankOrderDetailedConditionsPanel"};
        Pattern nsPattInst = Pattern.compile("(\\w+.*)\\.\\w+$");
        for (String tmpStr : nsArr) {
            System.out.print("提取名称空间：".concat(tmpStr));
            Matcher matcherInst = nsPattInst.matcher(tmpStr);
            if (matcherInst.find()) {
                System.out.print("\t原始串{" + tmpStr + "}名称空间{" + matcherInst.group(1) + "}\n");
            }
        }
    }
    /**
     * 匹配文件地址
     * @param srcPath srcPath
     */
    public void doMatchFilePath(String srcPath) {
        String pattern = "(\\w+.*\\\\)+(\\w+[.]{1}\\w+)+";
        Matcher matcher = Pattern.compile(pattern).matcher(srcPath);
        int groupCount = matcher.groupCount();
        while (matcher.find()) {
            for (int index = 0; index <= groupCount; index++) {
                System.out.println(index + ":" + matcher.group(index));
            }
        }
    }
    /**
     * 令人蛋疼的邮件地址
     * @param emailStr 邮件地址
     */
    public void doMatchEmail(String emailStr) {
        String regExp = "^(?=[^.])(?:\\w+[.]?)+@(([\\[](\\d+[.]?)+[\\]])|((\\w+[.]?)+[^.]){2,254})$";
        Matcher matcher = Pattern.compile(regExp).matcher(emailStr);
        if (matcher.matches()) {
            int groupCtn = matcher.groupCount();
            System.out.println(matcher.pattern().pattern() + "\t 匹配：" + emailStr);
            for (int i = 0; i < groupCtn; i++) {
                System.out.println("第" + i + "组：" + matcher.group(i));
            }
        }
    }
    /**
     * 文件名称匹配
     * @param fileName fileName
     */
    public void doMatchFileName(String fileName) {
        System.out.println("忽略文件扩展名:".concat(fileName.replaceAll("[.]{1}.*", "")));
        System.out.println("忽略文件名称,只取扩展名:".concat(fileName.replaceAll(".*[.]{1}", ".")));
    }

    /**
     * includeChinese
     * @param str str
     * @return rtnFlag true/false
     */
    public boolean includeChinese(String str) {
        String pattern = ".*[\u4e00-\u9fa5]+.*";
        boolean rtnFlag = false;
        if (str.matches(pattern)) {
            rtnFlag = true;
        }
        return rtnFlag;
    }

    /**
     * isDigit Number
     * @param str str
     * @return rtnFlag true/false
     */
    public boolean isDigitNumber(String str) {
        String pattern = "^\\d+.?\\d+$";
        return str.matches(pattern);
    }

    /**
     * isSignDigit Number
     * @param str str
     * @return rtnFlag true/false
     */
    public boolean isSignDigitNumber(String str) {
        String pattern = "^[+-]?\\d+.?\\d+$";
        return str.matches(pattern);
    }

    /**
     * 正则匹配组测试
     * @param pattInst 模式
     * @param srcStr 源匹配串
     */
    public void regGroupTest(Pattern pattInst, String srcStr) {
        Matcher maInst = pattInst.matcher(srcStr);
        while (maInst.find()) {
            System.out.println(maInst.group());
            int groupCount = maInst.groupCount();
            for (int index = 1; index <= groupCount; index++) {
                System.out.println("Group:" + index + ";\tGroupContent:" + maInst.group(index));
            }
        }
    }

    /**
     * 解析HTML字符串提取分组数据，格式：<br>
     * &lt;div class="className"&gt;
     * @throws IOException IOException
     */
    public void doAnalyseHTML() throws IOException {
        String srcStr = doLoadFile();
        //String regStr = "(<div\\sclass=\"drag\"[^>]+>[^<>]*(((?open<div[^>]*>)[^<>]*)+((?-open</div>)[^<>]*)+)*(?(open)(?!))</div>)+";
        String regStr = "<div\\sclass=\"drag\"[^>]*>(<div[^>]*>(.*)</div>|.)*?</div>";
        Matcher matcher = Pattern.compile(regStr, Pattern.DOTALL).matcher(srcStr);
        System.out.println("isMatcher:" + matcher.matches() + ":" + matcher.groupCount());
        while (matcher.find()) {
            int count = matcher.groupCount();
            for (int i = 0; i < count; i++) {
                System.out.println(i + ":" + matcher.group(i));
            }
        }
    }
    /**
     * 取html中input元素的值，且元素类型非hidden
     * <p>1、目前不支持元素值中包含'字符，如value='ab'c'</p>
     */
    public void doFetchInputValue() {
        String srcHTML = "<td><input type='text' value='12<3'/><input type='hidden' value='321'/><input type='text' value='456'/></td>";
        String regExpStr = "(<input\\s+)(type='[^h]+'\\s+)(value='([^']+)+')[^<]+";
        Matcher matcher = Pattern.compile(regExpStr).matcher(srcHTML);
        while (matcher.find()) {
            System.out.println("源字符串\t".concat(srcHTML));
            int count = matcher.groupCount();
            for (int i = 0; i <= count; i++) {
                System.out.println("第@T组匹配\t".replaceFirst("@T", "" + i).concat(matcher.group(i)));
            }
            System.out.println();
        }
    }
    /**
     * doLoadFile
     * @return fileContent fileContent
     * @throws IOException IOException
     */
    public String doLoadFile() throws IOException {
        InputStream ins = this.getClass().getResourceAsStream("htmlFile");
        String rtnStr = "";
        byte[] buff = new byte[1024];
        int count = -1;
        while ((count = ins.read(buff, 0, buff.length - 1)) != -1) {
            rtnStr += new String(buff, 0, count);
        }
        return rtnStr;
    }
    /**
     * 分析Sql条件
     * @param sql SQL
     */
    public void doAnalyseSql(String sql) {
        String andRegex = "([A|a][N|n][D|d])";
        String ignoreRegex = "(\\([^)]+\\))";
        String[] tmpStrArr = sql.split(andRegex);
        doPrint(tmpStrArr);
        Matcher matcher = Pattern.compile(ignoreRegex).matcher(sql);
        List<String> tmpList = new ArrayList<String>();
        while (matcher.find()) {
            tmpList.add(matcher.group(0));
        }
System.out.println(sql);
        sql = sql.replaceAll(ignoreRegex, "");
System.out.println(sql);
        sql = sql.replaceAll(andRegex, "");
System.out.println(sql);
    }
    /**
     * 取字符串中的\t、\r等标记
     * @param str str
     */
    public void doAnalyseFlag(String str) {
        //普通{找寻匹配元素}
        String reg1 = "(\r|\b|\n|\t)";
        Matcher matcher = Pattern.compile(reg1).matcher(str);
        while (matcher.find()) {
            int count = matcher.groupCount();
            for (int i = 0; i < count; i++) {
                str = matcher.replaceAll("\\\\" + matcher.group(i));
            }
        }
        System.out.println(str);
    }
    /**
     * 使用环视查找位置
     * @param str str
     */
    public void doAnalyseFlag2(String str) {
        //环视(找寻元素位置)
        String reg2 = "((?=\r))";
        str = Pattern.compile(reg2).matcher(str).replaceAll("这是位置");
        System.out.println(str);
    }
    /**
     * doPrint
     * @param tmpStrArr tmpStrArr
     */
    public void doPrint(String[] tmpStrArr) {
        for (String str : tmpStrArr) {
            System.out.println(str);
        }
    }
    /**
     * 测试入口
     * @param args 参数
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
//        String str = "abc\rsdfg\ndef\bsdfg\t";
//        RegExp regExpInst = new RegExp();
//        regExpInst.doMatchNamespace();
//        regExpInst.doMatchFileContent();
//        regExpInst.doFetchInputValue();
//        regExpInst.doAnalyseFlag(str);
//        regExpInst.doAnalyseFlag2(str);
        // System.out.println(regExpInst.includeChinese("工是"));
        // System.out.println("isNumber:".concat(String.valueOf(regExpInst.isDigitNumber("0.99"))));
        // System.out.println("isSignNumber:".concat(String.valueOf(regExpInst.isSignDigitNumber("-0.99"))));
        // System.out.println(Float.parseFloat("222"));
        // System.out.println("".equals(null));
        // regExpInst.doMatchFilePath("C:\\Program Files\\Adobe\\Flash.exe");
        // regExpInst.doMatchFileName("HelloWorld.txt");
        // regExpInst.regGroupTest(Pattern.compile("([A-Z]+)+(\\d+)+$"),
        // "D00101");
//         regExpInst.doAnalyseHTML();
//        regExpInst.doAnalyseHTML2();
//        String sql = "SELECT * FROM SY_TABLE_DEF WHERE 1=1 and 2=2 And 3=3 AND 4=4 AND (1=1 OR 2=2) OR (1=1 OR 1<2)";
//        regExpInst.doAnalyseSql(sql);
//        regExpInst.doMatchEmail("abc.abc.abc@abc.com");
//        regExpInst.doMatchEmail("abc.abc.abc@[172.16.0.15]");
    }
}
