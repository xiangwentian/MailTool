package com.workman.mail;

import com.workman.common.DateUtil;
import com.workman.common.ReadFile;
import com.workman.common.StringUtils;
import com.workman.data.StringProcessTool;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SendMailText_Picture_Enclosure {
    //发件人地址
    public static String senderAddress = "";
    //收件人地址 发送TO，抄送CC，密送BCC
    public static String recipientAddress_TO = "";
    public static String recipientAddress_CC = "";
    public static String recipientAddress_BCC = "";
    //发件人账户名
    public static String senderAccount = "";
    //发件人账户密码
    public static String senderPassword = "";
    //附件存放位置
    public static List filePath = null;
    //要拼接的html内容
    public static List<String> htmlText = null;
    //业务线名称
    public static String bussinessName = "";
    //失败告知的邮箱
//    public static String failRecipientAddress = "";

    public static String mailProtocol = "";
    public static String mailSmtp = "";
    public static String mailAuth = "";

    //邮件正文的标题
    public static String headline = "";
    //邮件正文的说明
    public static String explain = "";
    //业务邮件备注
    public static String comment = "";

    public SendMailText_Picture_Enclosure(Properties properties, Map email, List<String> htmlTextList) {

        this.senderAddress = properties.getProperty("mail.senderAddress");
        if (null != email) {
            this.recipientAddress_TO = String.valueOf(email.get("TO"));
            this.recipientAddress_CC = String.valueOf(email.get("CC"));
            this.recipientAddress_BCC = String.valueOf(email.get("BCC"));
        }
        this.senderAccount = properties.getProperty("mail.senderAccount");
        this.senderPassword = properties.getProperty("mail.senderPassword");

        this.bussinessName = properties.getProperty("business.name");
        this.htmlText = htmlTextList;
        this.mailProtocol = properties.getProperty("mail.transport.protocol");
        this.mailSmtp = properties.getProperty("mail.smtp.host");
        this.mailAuth = properties.getProperty("mail.smtp.auth");

        //邮件正文的标题
        this.headline = properties.getProperty("business.headline");
        //邮件正文的说明
        this.explain = properties.getProperty("business.explain");
        //业务邮件备注
        this.comment = properties.getProperty("bussiness.comment");

        try {
            if (!"true".equals(properties.getProperty("fail"))) {
                this.filePath = ReadFile.readfile(properties.getProperty("mail.FileDataSource") + "/" + DateUtil.getNowTime(DateUtil.DATEFORMAT8));//按时间维度来读取文件夹
            }
        } catch (IOException e) {
            //当附件为空时继续往下执行，不影响业务
            this.filePath = null;
        } catch (Exception e) {
            //e.printStackTrace();
            this.filePath = null;
        }
        processMsg(properties.getProperty("fail"));
    }

    public void processMsg(String isfail) {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", mailAuth);
        //设置传输协议
        props.setProperty("mail.transport.protocol", mailProtocol);
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", mailSmtp);
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        Message msg = null;
        try {
            if ("true".equals(isfail)) {
                msg = getFailMimeMessage(session);
            } else {
                //3、创建邮件的实例对象
                msg = getMimeMessage(session);
            }
            //4、根据session对象获取邮件传输对象Transport
            Transport transport = session.getTransport();
            //设置发件人的账户名和密码
            transport.connect(senderAddress, senderPassword);
            //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(msg, msg.getAllRecipients());

            //5、关闭邮件连接
            transport.close();
        } catch (Exception e) {

        }
    }


    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public MimeMessage getMimeMessage(Session session) throws Exception {
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址,senderAccount发件人展示别名
        msg.setFrom(new InternetAddress(senderAddress, senderAccount, "UTF-8"));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        InternetAddress[] parse = InternetAddress.parse(recipientAddress_TO);
        msg.setRecipients(MimeMessage.RecipientType.TO, parse);

        parse = InternetAddress.parse(recipientAddress_CC);
        msg.setRecipients(MimeMessage.RecipientType.CC, parse);

        parse = InternetAddress.parse(recipientAddress_BCC);
        msg.setRecipients(MimeMessage.RecipientType.BCC, parse);
        //4.设置邮件主题
        msg.setSubject(bussinessName, "UTF-8");

        //下面是设置邮件正文
        //msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");

        // 5. 创建图片"节点"
//        MimeBodyPart image = new MimeBodyPart();
//        // 读取本地文件
//        DataHandler dh = new DataHandler(new FileDataSource("resource\\mailTestPic.png"));
//        // 将图片数据添加到"节点"
//        image.setDataHandler(dh);
//        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
//        image.setContentID("mailTestPic");

//        // 6. 创建文本"节点"
////        MimeBodyPart text = new MimeBodyPart();
////        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
////        //text.setContent("<html><body><h1>中国人民</h1><h5>测试<font color=red>测试测试测试测试测试测</font>试测试测试测试测试测试测试测试测试测试</h5></body></html>", "text/html;charset=UTF-8");
////        text.setContent(htmlText, "text/html;charset=UTF-8");
////

        // 9. 创建附件"节点"
//        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
//        DataHandler dh2 = new DataHandler(new FileDataSource("D:/GaoyangMail/resource/mailTestDoc.docx"));
//        // 将附件数据添加到"节点"
//        attachment.setDataHandler(dh2);
//        // 设置附件的文件名（需要编码）
//        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));


        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();

        ////        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
////        MimeMultipart mm_text_image = new MimeMultipart();
////        mm_text_image.addBodyPart(text);
//////        mm_text_image.addBodyPart(image);
////        mm_text_image.setSubType("related");    // 关联关系
//        for (int i = 0; i < htmlText.size(); i++) {
        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        //text.setContent("<html><body><h1>中国人民</h1><h5>测试<font color=red>测试测试测试测试测试测</font>试测试测试测试测试测试测试测试测试测试</h5></body></html>", "text/html;charset=UTF-8");
        String html = "";

        //邮件正文的标题
//        this.headline = properties.getProperty("business.headline");
        //邮件正文的说明
//        this.explain = properties.getProperty("business.explain");
        //业务邮件备注
//        this.comment = properties.getProperty("bussiness.comment");
        //这里加邮件的标题、正文
        if (null != htmlText) {
            if (StringUtils.isNotEmpty(headline)) {
                headline = StringProcessTool.replaceParam2newParam(headline);
            }

            html += headline + explain;
            for (Object str : htmlText) {
                html += (String) str;
            }
            html += comment;
        }

        text.setContent(html, "text/html;charset=UTF-8");

        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
//        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // 关联关系
        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);
        mm.addBodyPart(text_image);
//        }

        if (null != filePath) {
            //多附件发送 start

            for (Object path : filePath) {
                try {
                    MimeBodyPart attachment = new MimeBodyPart();
                    DataHandler dh2 = new DataHandler(new FileDataSource(String.valueOf(path)));
                    // 将附件数据添加到"节点"

                    attachment.setDataHandler(dh2);

                    // 设置附件的文件名（需要编码）
                    attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
                    mm.addBodyPart(attachment);
                } catch (MessagingException e) {
                    continue;
                } catch (UnsupportedEncodingException e) {
                    continue;
                }
            }

            //多附件发送 end
        }

        //mm.addBodyPart(text_image);
        //mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getFailMimeMessage(Session session) throws Exception {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress, senderAccount, "UTF-8"));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        InternetAddress[] parse = InternetAddress.parse(recipientAddress_TO);
        msg.setRecipients(MimeMessage.RecipientType.TO, parse);
        //设置邮件主题
        msg.setSubject("告警邮件", "UTF-8");
        //设置邮件正文
        msg.setContent(bussinessName + "邮件发送失败，请检查！", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        //结束

        return msg;
    }

}
