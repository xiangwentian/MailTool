1、项目介绍  
技术交流liuzhuangjob@163.com  
基本JavaMail、jdbc实现的邮件发送脚本，主要用于定时获取报表统计数据并邮件业务人员，解放数据统计人员邮件，实现自动化  
2、功能介绍  
一、发送邮件（主送、抄送、密送）  
二、获取数据，通过sql获取报表数据统计整理成HTML格式邮件  
三、发送附件（获取指定目录下的文件，全部以附件形式加到邮件中）  
四、信息配置化，并可实时生效（邮件协议、发送邮箱信息、获取的数据sql、邮件主题、正文、备注配置化）  
五、数据带有特殊标识"_LHB"列根据相同参数值合并数据分组，并动态拼接HTML时合并单元格  
3、模块说明  
MailTool  
  	config 配置文件  
  	resource 附件目录  
  		201905xx 文件夹以时间格式yyyyMMdd格式创建，当时发送的邮件默认读取当时的日期文件夹中的文件，以附件形式发送  
  	src\main\java\com\workman  
  		common 工具类  
  		data 数据处理类  
  		mail 发送文件类  
  		MailMethod 启动类  
  		MailSubClass启动子类  
  	src\main\test 测试类  
  	
4、启动方式  
运行MailMethod方法  
或通过Maven打包执行 java -Dfile.encoding=utf-8 -jar MailTool-1.0-SNAPSHOT.jar  
避免中文乱码请执行jar时加上-Dfile.encoding=utf-8  
5、应用截图  
![数据html报表](https://github.com/xiangwentian/MailTool/blob/master/src/test/toolPic/mail1.png)  

![数据报表数据异常时发送告警邮件](https://github.com/xiangwentian/MailTool/blob/master/src/test/toolPic/%E5%91%8A%E8%AD%A6.png）
