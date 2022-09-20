package com.vm.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vm.controller.VendingMachineController;

@Configuration
@ComponentScan (basePackages = {"com.vm.controller"})
public class VendingMachineApplication {

	public static void main(String[] args) throws Exception {
//		 // Instantiate the UserIO implementation
//		UserIo myIo = new UserIoConsoleImpl();
//	    // Instantiate the View and wire the UserIO implementation into it
//		VendingMachineView myView = new VendingMachineView(myIo);
//	    // Instantiate the DAO
//		VendingMachineDao myDao = new VendingMachineDaoFileImpl();
//		// Instantiate the Audit DAO
//	    VendingMachineAuditDao myAuditDao = new VendingMachineAuditFileImpl();
//	    // Instantiate the Service Layer and wire the DAO and Audit DAO into it
//		VendingMachineServiceImpl myService = new VendingMachineServiceImpl(myDao, myAuditDao);
//	    // Instantiate the Controller and wire the Service Layer into it
//		VendingMachineController myController = new VendingMachineController(myService, myView);
//	    // Kick off the Controller
//		myController.run();
//		
		
		ApplicationContext ctx =  new ClassPathXmlApplicationContext("applicationContext.xml");
		VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
		 controller.run();
	}
}
