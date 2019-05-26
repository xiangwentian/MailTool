package com.workman.test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractMessageTest {
    public static void main(String[] args) {

//        String content = "456{{test}}123{{chart}}321";
//        String reg = "\\{\\{(.+?)\\}\\}";
//        String[] vars = GetRegResult(reg, content);//将content解析之后的数组
//        System.out.print(vars);//[{{test}},{{chart}}]
//        String str = "<h2 align=\"center\" style=\"font-size:12pt; font-family:Microsoft YaHei; color:#729aca\">\n" +
//                "中国电信与新媒体业务部交易明细[{today-2:yyyyMMdd}]</h2>";
//        String resultStr = processBracket(str);
//        parseData("today-2:yyyy-MM-dd");
    }

    /**
     * 该函数功能是正则匹配出所有结果到一个String数组中
     */
    public static String[] GetRegResult(String pattern, String Sourcecode) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Sourcecode);

        ArrayList<String> tempList = new ArrayList<String>();
        while (m.find()) {
            tempList.add(m.group());
        }
        String[] res = new String[tempList.size()];
        int i = 0;
        for (String temp : tempList) {
            res[i] = temp;
            i++;
        }
        return res;
    }
}
