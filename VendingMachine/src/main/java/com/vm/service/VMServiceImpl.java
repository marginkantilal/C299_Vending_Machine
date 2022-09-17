package com.vm.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vm.dao.VMAuditDao;
import com.vm.dao.VendingMachineDAO;
import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.DataValidationException;
import com.vm.exception.InsufficientFundsException;
import com.vm.exception.InvalidItemException;
import com.vm.exception.NoItemInventoryException;
import com.vm.exception.VendingMachinePersistenceException;

@Component
public class VMServiceImpl implements VMService {
	
	@Autowired
	private VendingMachineDAO dao;
	private VMAuditDao auditDao;
	
	
	//Initializes a constructor to inject dao and auditDao objects
		public VMServiceImpl(VendingMachineDAO dao, VMAuditDao auditDao) {
			this.dao = dao;
			this.auditDao = auditDao;
		}
		
		
		//Buy an item from the vending machine and return the change
		@Override
		public Change purchaseItems(String itemName, BigDecimal cash) throws
			VendingMachinePersistenceException, InsufficientFundsException, 
			NoItemInventoryException, DataValidationException, InvalidItemException{
			
			validateData(itemName, cash);
			Change change = null;
			
			if(dao.getItem(itemName) == null) {
				throw new InvalidItemException("Sorry, but this item does not exist in the vending machine");
			}
			
			BigDecimal itemPrice = dao.getItem(itemName).getPrice();
			int inventoryLevel = dao.getItem(itemName).getInventoryLevel();
			
			if(itemPrice.compareTo(cash) > 0) {
				throw new InsufficientFundsException("Sorry, but you don't have enough funds to buy this item\n");
			}
			else if(inventoryLevel == 0) {
				throw new NoItemInventoryException("Sorry, but this item is currently not in the inventory");
			}
			else {
				auditDao.writeAuditLogEntry(itemName);
				change = dao.purchaseItems(itemName, cash);
			}
			return change;
			
		}
		
		//Add item to the vending machine
		@Override
		public void addItem(Item item) throws DataValidationException, VendingMachinePersistenceException {
			
			if(item.getItemName() == null || item.getPrice() == null || item.getInventoryLevel() < 0) {
				throw new DataValidationException("ERROR: The item must have a name, price and valid quantity");
			}
			else {
				dao.addItem(item);
			}
		}
		//Returns all items from the vending machine and writes the time that this occured.
		@Override
		public List<Item> getAllItems() throws VendingMachinePersistenceException{
			auditDao.writeAuditLogEntry("All items have been retrieved");
			return dao.getAllItems();
			
		}
		
		//Checks whether the data that the user entered is valid
		private void validateData(String name, BigDecimal cash) throws DataValidationException {
			
			if(name == null || name.trim().length() == 0|| cash == null || cash.compareTo(BigDecimal.valueOf(0.00)) < 0) {
				throw new DataValidationException("ERROR: You must insert some cash and insert an existing item name\n");
			}
			return;
		}
	
	

}
