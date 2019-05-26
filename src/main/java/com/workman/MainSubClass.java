package com.workman;

import com.workman.common.PropertyReadTool;
import com.workman.common.ReadFile;
import com.workman.data.DataProcessTool;
import com.workman.data.JdbcTool;
import com.workman.data.ProbesProcess;
import com.workman.mail.SendMailText_Picture_Enclosure;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainSubClass {
    public static void run(String[] inpputParam) {
        PropertyReadTool propertyReadTool = new PropertyReadTool();

        Properties properties;
        if (inpputParam.length > 0) {
            //当inpputParam不为空时读取该参数中的jdbc.properties文件,例如:
            //java -Dfile.encoding=utf-8 -jarMailTool-1.0-SNAPSHOT.jar "D:/workman/config/jdbc.properties"
            properties = propertyReadTool.readProperty(inpputParam[0]);//文件路径写死
        } else {
            //默认读取当前上当下的config文件夹下的jdbc.properties配置文件
            //java -Dfile.encoding=utf-8 -jarMailTool-1.0-SNAPSHOT.jar
            properties = propertyReadTool.readProperty(ReadFile.readCurrentPath("config/jdbc.properties"));
        }

        JdbcTool jdbcTool = new JdbcTool(properties);


        try {
            //探针sql,先判断是否有数据需要发送
            //返回0表示数据异常、返回1表示数据正常、返回2表示该表格的数据为空(该表数据不作展示)
            boolean noDataProvide = ProbesProcess.ProbesSelect(properties);
            //当数据不为1时noDataProvide返回false
            if (!noDataProvide) {
                //noDataProvide返回false时,认为数据有问题,及时发送邮件到监控值班人,处理下面的逻辑:重发/手动发送
                properties.setProperty("mail.FileDataSource", "");
                properties.setProperty("fail", "true");
                Map sendMail = new HashMap();
                sendMail.put("TO", properties.getProperty("business.fail.mail"));//收件人支持多人
                new SendMailText_Picture_Enclosure(properties, sendMail, null);
                return;
            }

            List<String> result = new ArrayList<String>();
            //要发送的数据sql全部放到配置文件的jdbc.sql参数中,以sql路径的形式传入,具体格式请见jdbc.properties配置文件
            if (properties.getProperty("jdbc.sql").indexOf(",") != -1) {
                //当是多个sql文件时走这里(带标题的sql,形式:sql -> sql默认为一个sql)
                String[] sqls = properties.getProperty("jdbc.sql").split(",");
                for (int i = 0; i < sqls.length; i++) {
                    //联合sql使用"->"标识分隔,主要用做带标题的数据:标题+数据内容格式,具体请参看github应用截图
                    if (String.valueOf(sqls[i]).indexOf("->") != -1) {//当sql有联合sql时
                        String[] unionSql = String.valueOf(sqls[i]).split("->");//properties.getProperty("jdbc.sql").split("->");
                        String htmlText = DataProcessTool.List2HtmlHasTitle(jdbcTool.getData(ReadFile.readSql(unionSql[0].trim())), jdbcTool.getData(ReadFile.readSql(unionSql[1].trim())));
                        result.add(htmlText);
                    } else {
                        //当只是展示数据列表时逻辑执行走这里,具体请参看github应用截图
                        String sql = ReadFile.readSql(String.valueOf(sqls[i]));
                        List resultData = jdbcTool.getData(String.valueOf(sql));//jdbcTool.getData(args[0]);
                        //result.add(resultData);
                        String htmlText = DataProcessTool.List2Html(null, resultData);
                        result.add(htmlText);
                    }

                }
            } else {
                //只是一条sql要执行时,(带标题的sql,形式:sql -> sql默认为一个sql)
                if (properties.getProperty("jdbc.sql").indexOf("->") != -1) {//当sql有联合sql时
                    String[] unionSql = properties.getProperty("jdbc.sql").split("->");
                    //DataProcessTool.List2HtmlHasTitle把sql拼接成html形式
                    String htmlText = DataProcessTool.List2HtmlHasTitle(jdbcTool.getData(ReadFile.readSql(unionSql[0].trim())), jdbcTool.getData(ReadFile.readSql(unionSql[1].trim())));
                    result.add(htmlText);
                } else {
                    String sql = ReadFile.readSql(properties.getProperty("jdbc.sql"));
                    List resultData = jdbcTool.getData(sql);
                    //把sql拼接成html形式
                    String htmlText = DataProcessTool.List2Html(null, resultData);
                    result.add(htmlText);
                }

            }

            //发送邮件
            Map email = new HashMap();
            email.put("TO", properties.getProperty("MimeMessage.RecipientType.TO"));//接收者邮箱
            email.put("CC", properties.getProperty("MimeMessage.RecipientType.CC"));//抄送者邮箱
            email.put("BCC", properties.getProperty("MimeMessage.RecipientType.BCC"));//密送者邮箱

            try {
                //发送邮件方法
                new SendMailText_Picture_Enclosure(properties, email, result);
            } catch (Exception e) {
                //当发送数据异常时，给值班同学告警
                try {
                    properties.setProperty("mail.FileDataSource", "");
                    properties.setProperty("fail", "true");
                    //properties.setProperty("TO", properties.getProperty("business.fail.mail"));
                    Map sendMail = new HashMap();
                    sendMail.put("TO", properties.getProperty("business.fail.mail"));
                    new SendMailText_Picture_Enclosure(properties, sendMail, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }

        } catch (IOException e) {
            try {
                properties.setProperty("mail.FileDataSource", "");
                properties.setProperty("fail", "true");
                Map sendMail = new HashMap();
                sendMail.put("TO", properties.getProperty("business.fail.mail"));
                new SendMailText_Picture_Enclosure(properties, sendMail, null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                properties.setProperty("mail.FileDataSource", "");
                properties.setProperty("fail", "true");
                Map sendMail = new HashMap();
                sendMail.put("TO", properties.getProperty("business.fail.mail"));
                new SendMailText_Picture_Enclosure(properties, sendMail, null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {

        }
    }
}
