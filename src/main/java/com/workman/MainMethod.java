package com.workman;

/**
 * 邮件系统主类
 */
public class MainMethod {
    public static void main(String[] args) {
        MainSubClass mainClass = new MainSubClass();
        //运行文件处理方法
        mainClass.run(args);
    }

}
