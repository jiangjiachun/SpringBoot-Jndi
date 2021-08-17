# SpringBoot  Jndi 例子
1、内嵌Tomcat启动：spring.profiles.active=dev

2、外置Tomcat启动：spring.profiles.active=prod，配置conf/context.xml，增加

```xml
<Resource auth="test" driverClassName="org.h2.Driver" maxIdle="4" maxTotal="8" name="jdbc/test" username="sa" password="" type="javax.sql.DataSource" url="jdbc:h2:file:~/test"/>
```
