package com.vm.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vm.controller.VendingMachineController;




public class VendingMachineApplication {

	public static void main(String[] args) throws Exception {
	
		//Use the XML file for configuration and inject objects into classes
				ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
				VendingMachineController controller = app.getBean("controller", VendingMachineController.class);
				controller.run();
				controller.showAllItems();
	}

}
