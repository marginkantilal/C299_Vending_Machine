package com.vm.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vm.dto.Change;
import com.vm.dto.Item;
import com.vm.exception.VendingMachinePersistenceException;

class VMDaoImplTest {
	
	VendingMachineDAO testDao;
	
	public VMDaoImplTest() {
		
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	 @BeforeEach
	    public void setUp() throws IOException {
	    	String testFile = "TestVMItems.txt";
	    	new FileWriter(testFile);
	    	testDao = new VMDaoImpl(testFile);
	    }

	@AfterEach
	void tearDown() throws Exception {
	}

	// Testing  add single item
	
	@Test
	public void testAddBuyItem() throws VendingMachinePersistenceException  {
		
		Item item = new Item();
		item.setItemName("Walkers");
		item.setPrice(new BigDecimal("1.00"));
		item.setInventoryLevel(10);
		
		testDao.addItem(item);
		Item receivedAddedItem = testDao.getItem(item.getItemName());
		
		assertEquals(receivedAddedItem.getItemName(), item.getItemName(), "Checking item names");
		assertEquals(receivedAddedItem.getPrice(), item.getPrice(), "Checking item prices");
		assertEquals(receivedAddedItem.getInventoryLevel(), item.getInventoryLevel(), "Check inventory levels");
		
		BigDecimal cash = new BigDecimal("3.00");
		
		Change receivedChange = testDao.buyItem(item.getItemName(), cash);
		BigDecimal price = item.getPrice();
		Item receivedItem = testDao.getItem(item.getItemName());
		
		assertEquals(receivedItem.getInventoryLevel() + 1, item.getInventoryLevel(), "Inventory level must be reduced by 1");
		assertEquals(cash.subtract(price), receivedChange.getTotalChange(), "Change returned must be correct");
	}
	
	
	@Test
	public void testAddBuyItems() throws VendingMachinePersistenceException  {
		
		Item item = new Item();
		item.setItemName("Test ONE");
		item.setPrice(new BigDecimal("1.00"));
		item.setInventoryLevel(8);
		
		Item Moreitem = new Item();
		Moreitem.setItemName("Test TWO");
		Moreitem.setPrice(new BigDecimal("1.00"));
		Moreitem.setInventoryLevel(8);
		
		testDao.addItem(item);
		testDao.addItem(Moreitem);
		System.out.println(testDao.getAllItems());
		
		List<Item>items = testDao.getAllItems();
		
		
		assertNotNull(items, "The list should not be empty or null");
		assertEquals(2, items.size(), "It should only have 2 items in the list");
		assertTrue(items.contains(item), "Item Walker should be in the list");
		assertTrue(items.contains(Moreitem), "Item Sprite should be in the list");
		
		
		BigDecimal cash = new BigDecimal("15.00");
		
		
		Change getChangeForItemOne = testDao.buyItem(item.getItemName(), cash);
		Change getChangeForItemTwo = testDao.buyItem(item.getItemName(), cash);

		BigDecimal priceForItemOne = item.getPrice();
		BigDecimal priceForItemTwo = item.getPrice();
		
		Item getItemOne = testDao.getItem(item.getItemName());
		Item getItemTwo = testDao.getItem(item.getItemName());

		
		assertEquals(getItemOne.getInventoryLevel() + 1, item.getInventoryLevel(), "Inventory level must be reduced by 1");
		assertEquals(getItemTwo.getInventoryLevel() + 1, item.getInventoryLevel(), "Inventory level must be reduced by 1");

		assertEquals(cash.subtract(priceForItemOne), getChangeForItemOne.getTotalChange(), "Change returned must be correct");
		assertEquals(cash.subtract(priceForItemTwo), getChangeForItemTwo.getTotalChange(), "Change returned must be correct");

	}
	

}
