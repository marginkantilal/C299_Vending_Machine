package com.vm.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.DataValidationException;
import com.vm.exception.InsufficientFundsException;
import com.vm.exception.InvalidItemException;
import com.vm.exception.NoItemInventoryException;
import com.vm.exception.VendingMachinePersistenceException;

public interface VMService {
	Change purchaseItems(String name, BigDecimal cash) throws
	InsufficientFundsException,
	NoItemInventoryException,
	VendingMachinePersistenceException,
	DataValidationException,
	InvalidItemException;
	
	List<Item> getAllItems() throws
	VendingMachinePersistenceException;

	void addItem(Item item) throws DataValidationException, VendingMachinePersistenceException, InvalidItemException;
	
	
	
}
