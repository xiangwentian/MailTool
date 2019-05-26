package com.workman.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public ReadFile() {
    }

    /**
     * 读取某个文件夹下的所有文件
     */
    public static List readfile(String filepath) throws FileNotFoundException, IOException {
        List filePath = new ArrayList();
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                //处理一下当以时间结尾的文件时filepath返回null
                if (StringUtils.strIsLastEnd(file.getPath(), DateUtil.getNowTime(DateUtil.DATEFORMAT8))) {
                    //如果为true，说明不有附件，直接退出
                    return null;
                }
                //获取path放到list中
                filePath.add(file.getPath());
            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        //获取path放到list中
                        filePath.add(readfile.getPath());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return filePath;
    }

    public static String readSql(String filepath) throws IOException {
        File file = new File(filepath);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        //new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     */


        /*public static boolean deletefile(String delpath)
                        throws FileNotFoundException, IOException {
                try {

                        File file = new File(delpath);
                        if (!file.isDirectory()) {
                                System.out.println("1");
                                file.delete();
                        } else if (file.isDirectory()) {
                                System.out.println("2");
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File delfile = new File(delpath + "\\" + filelist[i]);
                                        if (!delfile.isDirectory()) {
                                                System.out.println("path=" + delfile.getPath());
                                                System.out.println("absolutepath="
                                                                + delfile.getAbsolutePath());
                                                System.out.println("name=" + delfile.getName());
                                                delfile.delete();
                                                System.out.println("删除文件成功");
                                        } else if (delfile.isDirectory()) {
                                                deletefile(delpath + "\\" + filelist[i]);
                                        }
                                }
                                file.delete();

                        }

                } catch (FileNotFoundException e) {
                        System.out.println("deletefile()   Exception:" + e.getMessage());
                }
                return true;
        }*/
    public static String readCurrentPath(String fileName) {
        String AbsoluteCurrentPath = "";
        File directory = new File(fileName);
        // String getCanonicalPath = directory.getCanonicalPath(); //得到的是C:/test/abc
        //String getAbsolutePath = directory.getAbsolutePath();    //得到的是C:/test/abc
        AbsoluteCurrentPath = directory.getAbsolutePath();    //得到的是C:/test/abc
        // String absolutePath = directory.getPath();                    //得到的是abc
        return AbsoluteCurrentPath;
    }

    public static void main(String[] args) {
        try {
            readSql("D:/MailTool/data_day.sql");
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        System.out.println("ok");
    }
}
