package com.vm.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vm.dao.VendingMachineAuditDao;
import com.vm.dao.VendingMachineAuditFileImpl;
import com.vm.dao.VendingMachineDAO;
import com.vm.dto.Item;
import com.vm.exception.DataValidationException;
import com.vm.exception.InsufficientFundsException;
import com.vm.exception.InvalidItemException;
import com.vm.exception.NoItemInventoryException;
import com.vm.exception.VendingMachinePersistenceException;


class VMServiceImplTest {

	private VMService service;
	
	public VMServiceImplTest() {
		VendingMachineDAO dao = new VMDaoStubImpl();
		VendingMachineAuditDao auditDao = new VendingMachineAuditFileImpl();
		
		service = new VMServiceImpl(dao, auditDao);
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    public void addValidItem() throws Exception{
    	
    	Item item = new Item();
    	item.setItemName(""
    			+ "Fanta");
    	item.setPrice(new BigDecimal("1.00"));
    	item.setInventoryLevel(8);
    	
    	try {
    		service.addItem(item);
    		return;
    	}
    	catch(Exception e) {
    		fail("Could not add an item");
    	}
    	
    }
	
	@Test
	public void buyInvalidItem() throws Exception{
		String name = "Dummy";
		BigDecimal cash = new BigDecimal("2.50");
		
	
    	try {
    		service.buyItem(name, cash);
    		fail("InvalidItemException was not thrown");
    	}
    	catch(InsufficientFundsException | VendingMachinePersistenceException | DataValidationException | NoItemInventoryException e) {
    		fail("Incorrect exception was thrown");
    	}
    	catch(InvalidItemException e) {
    		return;
    	}
  

	}

	 @Test
	    public void buyValidItem() throws Exception{
	    	
	    	String itemName = "Fanta";
	    	BigDecimal cash = new BigDecimal("2.00");
	    	
	    	try {
	    		service.buyItem(itemName, cash);
	    	}
	    	catch (InsufficientFundsException | NoItemInventoryException | VendingMachinePersistenceException | DataValidationException e){
	    		fail("Item and money were valid, no exception should've occured here");
	    	}
	    }
	
	 
	
	 
}
