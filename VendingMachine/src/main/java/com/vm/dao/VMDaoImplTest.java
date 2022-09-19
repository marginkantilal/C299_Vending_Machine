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
		assertEquals(receivedAddedItem.getInventoryLevel(), item.getInventoryLevel(), "Check inventory levels and should be 10");
		
		BigDecimal cash = new BigDecimal("3.00");
		
		Change giveChange = testDao.buyItem(item.getItemName(), cash);
		BigDecimal price = item.getPrice();
		Item getItem = testDao.getItem(item.getItemName());
		
		assertEquals(getItem.getInventoryLevel() + 1, item.getInventoryLevel(), "Inventory level must be reduced by 1");
		assertEquals(cash.subtract(price), giveChange.getTotalChange(), "Change returned must be correct");
	}
	
	
	@Test
	public void testAddBuyItems() throws VendingMachinePersistenceException  {
		
		Item item = new Item();
		item.setItemName("Walker");
		item.setPrice(new BigDecimal("1.00"));
		item.setInventoryLevel(7);
		
		Item Moreitem = new Item();
		Moreitem.setItemName("Sprite");
		Moreitem.setPrice(new BigDecimal("0.80"));
		Moreitem.setInventoryLevel(8);
		
		testDao.addItem(item);
		testDao.addItem(Moreitem);
		
		List<Item>items = testDao.getAllItems();
		assertNotNull(items, "The list should not be empty or null");
		assertEquals(2, items.size(), "It should only have 2 items in the list");
		assertTrue(items.contains(item), "Item Walker should be in the list");
		assertTrue(items.contains(Moreitem), "Item Sprite should be in the list");
		
		
		BigDecimal cash = new BigDecimal("5.00");
		
		Change getChangeForItemOne = testDao.buyItem(item.getItemName(), cash);
		Change getChangeForItemTwo = testDao.buyItem(Moreitem.getItemName(), cash);

		BigDecimal priceForItemOne = item.getPrice();
		BigDecimal priceForItemTwo = Moreitem.getPrice();
		
		Item getItemOne = testDao.getItem(item.getItemName());
		Item getItemTwo = testDao.getItem(Moreitem.getItemName());


		assertEquals(getItemOne.getInventoryLevel() + 1, item.getInventoryLevel(), "Quantity should be reduced by 1");
		assertEquals(getItemTwo.getInventoryLevel() + 1, Moreitem.getInventoryLevel(), "Quantity should be reduced by 2");

		assertEquals(cash.subtract(priceForItemOne), getChangeForItemOne.getTotalChange(), "Correct Change  must be given after buying item one");
		assertEquals(cash.subtract(priceForItemTwo), getChangeForItemTwo.getTotalChange(), "Correct Change  must be given");

	}
	

}
