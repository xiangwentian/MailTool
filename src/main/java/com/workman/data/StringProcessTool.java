package com.workman.data;

import com.workman.common.DateUtil;
import com.workman.common.StringUtils;

import java.util.ArrayList;

public class StringProcessTool {

    public static int firstStr;
    public static int lastStr;

    public static String replaceParam2newParam(String param) {
        String newStr = "";
        if (param.contains("{")) {
            newStr = processTime(processBracket(param));//replaceParam2newParam(processTime(processBracket(param)));
        }
        if (StringUtils.isEmpty(newStr)) {
            return newStr;
        }
        System.out.println("newStr:" + newStr);
        String replaceParam = processBracket(param);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(replaceParam);
        sb.append("}");
        System.out.println("sb:" + sb.toString());

        System.out.println("replaceParam:" + replaceParam);
        param = param.replace(sb.toString(), newStr);
        return param;
    }

    public static String processTime(String param) {
        String resultTime = "";
        if (StringUtils.isNotEmpty(param)) {
            //param 格式例如：today-2:yyyyMMdd
            String[] params = param.split(":");
            //不为空时，处理时间
            if (params[0].contains("-")) {
                String timePara = params[0].split("today-")[1];//today-,2
                resultTime = DateUtil.processTimeParam(Integer.valueOf(timePara), params[1]);
                // System.out.println("resultTime:" + resultTime);
            } else {
                resultTime = DateUtil.processTimeParam(0, params[1]);
            }
        }
        return resultTime;
    }

    public static String processBracket(String str) {
        ArrayList<String> word = new ArrayList<String>();
        int m = 0, n = 0;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '{') {
                if (count == 0) {
                    m = i + 1;
                    firstStr = i;
                }
                count++;
            }
            if (str.charAt(i) == '}') {
                count--;
                if (count == 0) {
                    n = i;
                    lastStr = n + 1;
                    word.add(str.substring(m, n));
                }
            }
        }
        String result = "";
        for (String a : word) {
            System.out.println(a);
            result = a;
            break;
        }
        return result;
    }

    public static void main(String[] args) {
        String param = "<h2 align=\\\"center\\\" style=\\\"font-size:12pt; font-family:Microsoft YaHei; color:#729aca\\\">\\n\" +\n" +
                "//                \"标题[{today:yyyyMMdd}]</h2>";
        String result = replaceParam2newParam(param);
        System.out.println("result:" + result);
    }
}
