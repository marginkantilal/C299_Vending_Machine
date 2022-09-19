package com.vm.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Iterator;

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
    public void testAddValidItem() throws Exception{
    	
    	Item item = new Item();
    	item.setItemName("Fanta");
    	item.setPrice(new BigDecimal("1.00"));
    	item.setInventoryLevel(8);   	
    	try {
    		service.addItem(item);
    	}
    	catch(Exception e) {
    		fail("Could not add an item");
    	}
    	
    }
	
	@Test
    public void testAddInValidItemWithoutItemPrice() throws Exception{
    	
    	Item item = new Item();
    	item.setItemName("Fanta");
    	item.setInventoryLevel(8);   	
    	try {
    		service.addItem(item);
    		fail("Could not add an item because it is missing the price");

    	}
    	catch(DataValidationException e) {

    	}
    	
    }
	
	@Test
	public void testBuyInvalidItem() throws Exception{
		String name = "Dummy";
		BigDecimal cash = new BigDecimal("2.50");
		
	
    	try {
    		service.buyItem(name, cash);
    		fail("InvalidItemException was not thrown because it is invalid item");
    	}
    	catch(InsufficientFundsException | VendingMachinePersistenceException | DataValidationException | NoItemInventoryException e) {
    		fail("Incorrect exception will be thrown");
    	}
    	catch(InvalidItemException e) {
    	}
  

	}

	 @Test
	    public void testBuyValidItem() throws Exception{
	    	
	    	String itemName = "Fanta";
	    	BigDecimal cash = new BigDecimal("2.00");
	    	
	    	try {
	    		service.buyItem(itemName, cash);

	    	}
	    	catch (InsufficientFundsException | NoItemInventoryException | VendingMachinePersistenceException | DataValidationException e){
	    		fail("Out of stock or not enough money");
	    	}
	    }
	 @Test
	    public void testBuyItemWithNotEnoughMoney() throws Exception{
	    	String itemName = "Fanta";
	    	BigDecimal cash = new BigDecimal("1.00");
	    	
	    	try {
	    		service.buyItem(itemName, cash);
	    		fail("InsufficientFundsException was not thrown, because cash is enough to buy the item");
	    	}
	    	catch (NoItemInventoryException | VendingMachinePersistenceException | DataValidationException | InvalidItemException e) {
	    		fail("Incorrect Exception will be thrown");
	    	}
	    	catch(InsufficientFundsException e) {
	    		return;
	    	}
	    }
	 	 
	@Test
	public void testBuyOutOfStockItem()throws Exception{
		String itemName="Fanta";
    	BigDecimal cash = new BigDecimal("3.00");
    	
    	for (int i = 0; i < 10; i++) {
			service.buyItem(itemName, cash);
		}
    	
    	
    	try {
    		service.buyItem(itemName, cash);
    		fail("NoItemInventoryException was not thrown because there is item in the stock");
    	}catch(NoItemInventoryException e) {
    		return;
    	}

	}
	 
}
