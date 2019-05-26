package com.workman.test;

import java.io.File;
import java.io.IOException;

public class AbsolutePathTest {
    public static void main(String[] args) {
//        File directory = new File("sql/jdbc.properties");
//        try {
//            String getCanonicalPath = directory.getCanonicalPath(); //得到的是C:/test/abc
//            String getAbsolutePath = directory.getAbsolutePath();    //得到的是C:/test/abc
//            String absolutePath = directory.getPath();                    //得到的是abc
//            System.out.println("getCanonicalPath:" + getCanonicalPath + ",getAbsolutePath:" + getAbsolutePath + ",absolutePath:" + absolutePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String AbsoluteCurrentPath = "";
        String fileName = "sql/" + "jdbc.properties";
        File directory = new File(fileName);
//        try {
        // String getCanonicalPath = directory.getCanonicalPath(); //得到的是C:/test/abc
        //String getAbsolutePath = directory.getAbsolutePath();    //得到的是C:/test/abc
        AbsoluteCurrentPath = directory.getAbsolutePath();    //得到的是C:/test/abc
        // String absolutePath = directory.getPath();                    //得到的是abc
        //System.out.println("getCanonicalPath:" + getCanonicalPath + ",getAbsolutePath:" + getAbsolutePath + ",absolutePath:" + absolutePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("AbsoluteCurrentPath:" + AbsoluteCurrentPath);
    }
}
