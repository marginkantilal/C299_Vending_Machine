package com.vm.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.VendingMachinePersistenceException;
import com.vm.service.VMService;
import com.vm.view.VMView;


@Component
public class VendingMachineController {
	@Autowired
    private VMService service;
    @Autowired
    private VMView view;
    
    
  //Use constructor based dependency injection to connect service layer and view to the controller
  	public VendingMachineController(VMService service, VMView view) {
  		this.service = service;
  		this.view = view;
  	}
  	
  	//Run the main program
  	public void run() throws Exception {
  		
  		showAllItems();
  		BigDecimal fundsToBeAdd = view.addFunds();
  		String userChoice = view.chooseItem();
  		Change change = service.purchaseItems(userChoice, fundsToBeAdd);
  		view.displayChange(change.getCoins());
  		
  		
  	}
  	
  	//Return all items from the vending machine
  	public void showAllItems() throws VendingMachinePersistenceException {
  		
  		List<Item> items = service.getAllItems();
  		view.displayAllItems(items);
  	}
  	

}
