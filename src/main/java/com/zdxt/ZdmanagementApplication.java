package com.zdxt;

import com.zdxt.common.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;
@MapperScan(basePackages = ("com.zdxt.mapper"))
@ComponentScan(basePackages={"com"})
@SpringBootApplication
public class ZdmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZdmanagementApplication.class, args);
	}
	@Bean
	public IdWorker idWorker(){return new IdWorker();}
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("5MB");
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("500MB");
		return factory.createMultipartConfig();
	}
}
