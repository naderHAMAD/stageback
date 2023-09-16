package com.test.spring;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.test.spring.controller.WorkflowController;


@SpringBootApplication
@PropertySource("classpath:server.properties")
public class FirstSpringApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext = SpringApplication.run(FirstSpringApplication.class, args);
		
		Server server= applicationContext.getBean("server",Server.class);
		WorkflowController controller=new WorkflowController(server);
		
	}
	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/employees").allowedOrigins("http://localhost:4200");
			}
		};
	}*/
}
