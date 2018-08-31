package com.huaguoguo;

import org.apache.tomcat.websocket.WsSession;
import org.apache.tomcat.websocket.pojo.Constants;
import org.apache.tomcat.websocket.pojo.PojoMethodMapping;
import org.apache.tomcat.websocket.server.WsFrameServer;
import org.apache.tomcat.websocket.server.WsRemoteEndpointImplServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpSession;
import javax.servlet.http.WebConnection;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Arrays;

@SpringBootApplication
@EnableCaching
public class HellohuaguoguoApplication{



	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		SpringApplication.run(HellohuaguoguoApplication.class, args);
	}


}
