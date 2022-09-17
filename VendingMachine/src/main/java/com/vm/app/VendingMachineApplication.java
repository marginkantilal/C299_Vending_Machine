package com.vm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vm.controller.VendingMachineController;




@SpringBootApplication
public class VendingMachineApplication {

	public static void main(String[] args) throws Exception {
	
		//Use the XML file for configuration and inject objects into classes
				ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
				VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
				controller.run();
	}

}
