package com.workman.common;

/**
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

import java.io.UnsupportedEncodingException;

/**
 * @author yuanfeng
 * @version $Id: StringUtils.java, v 0.1 2016年4月25日 下午9:13:44 yuanfeng Exp $
 */
public class StringUtils {

    /**
     * 判断str是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断str是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 隐藏关键信息
     *
     * @param data       隐藏字符串
     * @param startIndex 显示前几位
     * @param endIndex   显示后几位
     * @return
     */
    public static String StringHideInfo(String data, int startIndex, int endIndex) {
        StringBuffer hideString = new StringBuffer();
        if (StringUtils.isNotEmpty(data)) {
            if (data.length() > (startIndex + endIndex)) {
                hideString.append(data.substring(0, startIndex));
                for (int i = 0; i < data.length() - (startIndex + endIndex); i++) {
                    hideString.append("*");
                }
                hideString.append(data.substring(data.length() - endIndex, data.length()));
                return hideString.toString();
            }
        }
        return data;
    }

    public static boolean areNotEmpty(String[] values) {
        boolean result = true;
        if ((values == null) || (values.length == 0)) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    public static String toUnderlineStyle(String name) {
        StringBuilder newName = new StringBuilder();
        int len = name.length();
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    newName.append("_");
                }
                newName.append(Character.toLowerCase(c));
            } else {
                newName.append(c);
            }
        }
        return newName.toString();
    }

    public static String toCamelStyle(String name) {
        StringBuilder newName = new StringBuilder();
        int len = name.length();
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (i == 0) {
                newName.append(Character.toLowerCase(c));
            } else {
                newName.append(c);
            }
        }
        return newName.toString();
    }

    public static String escapeXml(String value) {
        StringBuilder writer = new StringBuilder();
        char[] chars = value.trim().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
                case '<':
                    writer.append("&lt;");
                    break;
                case '>':
                    writer.append("&gt;");
                    break;
                case '\'':
                    writer.append("&apos;");
                    break;
                case '&':
                    writer.append("&amp;");
                    break;
                case '"':
                    writer.append("&quot;");
                    break;
                default:
                    if ((c != '\t') && (c != '\n') && (c != '\r') && ((c < ' ') || (c > 55295))
                            && ((c < 57344) || (c > 65533)) && ((c < 65536) || (c > 1114111)))
                        continue;
                    writer.append(c);
            }
        }
        return writer.toString();
    }

    /***
     * 按字节截取字符串(汉字2字节)，截取后加“...”
     *
     * @param str 原串
     * @param subSLength 截取长度
     * @return
     */
    public static String subStrByLength(String str, int subSLength) {
        try {
            if (isEmpty(str)) {
                return "";
            } else {
                //截取字节数
                int tempSubLength = subSLength;
                //截取的子串  
                String subStr = str.substring(0,
                        str.length() < subSLength ? str.length() : subSLength);
                int subStrByetsL = subStr.getBytes("GBK").length;
                //如果字符小于要截取的长度，直接返回
                if (subStrByetsL <= subSLength) {
                    return str;
                }
                // 说明截取的字符串中包含有汉字  
                while (subStrByetsL > tempSubLength) {
                    int subSLengthTemp = --subSLength;
                    subStr = str.substring(0,
                            subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
                    subStrByetsL = subStr.getBytes("GBK").length;
                }
                return subStr + "...";
            }
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @return int 得到的字符串长度
     */
    public static int charlength(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;

    }

    /**
     * 匹配 path中是否以matchingParam结尾，如果是返回true
     *
     * @param path
     * @param matchingParam
     * @return
     */
    public static boolean strIsLastEnd(String path, String matchingParam) {
        int end = path.lastIndexOf(matchingParam);
        int len = matchingParam.length();
        if (end == path.length() - len) {
            return true;
        } else {
            return false;
        }
    }
}
