package com.workman.test;

import com.workman.common.DateUtil;

public class StrTest {

    public static void main(String[] args) {
        if (isChineseByBlock(Character.valueOf('，'))) {
            System.out.println("true");
        } else {
            System.out.println("fail");
        }
    }

    public static boolean isChineseByBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
    }

    public static void strEndwithTest() {
        String path = "D:\\GaoyangMail\\resource\\20190523";
//        if (path.lastIndexOf(DateUtil.getNowTime(DateUtil.DATEFORMAT8)) != -1) {
//        if (path.lastIndexOf("resource") != -1) {
//            System.out.println("true");
//        } else {
//            System.out.println("fail");
//        }
        String matchingParam = DateUtil.getNowTime(DateUtil.DATEFORMAT8);
        int end = path.lastIndexOf(matchingParam);
        int len = matchingParam.length();
        if (end == path.length() - len) {
            System.out.println("结尾是abc");
        } else {
            System.out.println("结尾不是abc");
        }
    }
}
