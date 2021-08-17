package com.sample;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;

@SpringBootApplication
public class SpringBootJndiApplication {
    
    @Value("${jndi.driverClassName}")
    private String driverClassName;
    
    @Value("${jndi.url}")
    private String url;
    
    @Value("${jndi.username}")
    private String username;
    
    @Value("${jndi.password}")
    private String password;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJndiApplication.class, args);
	}
	
    @Bean
    @Profile("dev")
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming(); 
                return super.getTomcatWebServer(tomcat);
            }
    
            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/test");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", driverClassName);
                resource.setProperty("url", url);
                resource.setProperty("username", username);
                resource.setProperty("password", password);
                context.getNamingResources().addResource(resource);
            }
    
        };
    }
    
    @Bean(destroyMethod="")
    @Profile("dev")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/test");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }

}
