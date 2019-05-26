package com.workman.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReadTool {
    public Properties readProperty(String properUrl) {
        //读取属性文件xx.properties
        Properties properties = new Properties();
        try {
            // 使用InPutStream流读取properties文件
            BufferedReader bufferedReader = new BufferedReader(new FileReader(properUrl));
            properties.load(bufferedReader);
            // 获取key对应的value值
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
