package com.acertaininventorymanager.business;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.acertaininventorymanager.interfaces.ItemDataManager;
import com.acertaininventorymanager.utils.InexistentItemPurchaseException;
import com.acertaininventorymanager.utils.InventoryConstants;
import com.acertaininventorymanager.utils.InventoryManagerException;

public class ConcreteItemDataManager implements ItemDataManager {

	public Map<Integer, Item> itemMap = new HashMap<Integer, Item>();
	public Map<String, ItemPurchase> itemPurchaseMap;
	private final ReadWriteLock itemPurchaseLock = new ReentrantReadWriteLock();

	public ConcreteItemDataManager() {
		itemMap = populateItemMap();
		itemPurchaseMap = new HashMap<String, ItemPurchase>();
	}

	private Map<Integer, Item> populateItemMap() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		HashMap<Integer, Item> itemMap = new HashMap<>();

		/* Populate data for items */
		for (int index = 0; index < InventoryConstants.NUMBER_ITEMS; index++) {
			int itemID = index;
			int itemPrice = rand.nextInt(InventoryConstants.ITEM_PRICE_RANGE);
			int itemQuantity = InventoryConstants.ITEM_QUANTITY;
			Item item = new Item(itemID, itemQuantity, itemPrice);
			itemMap.put(itemID, item);
		}
		return itemMap;
	}

	@Override
	public void addItemPurchase(ItemPurchase itemPurchase) throws InventoryManagerException {
		// orderId, customerId, itemId together form an identifier for an item
		// purchase.
		// Since I need an unique key for each itemPurchase, the sum of orderId,
		// customerId, itemId will be uses as a key for hashMap
		itemPurchaseLock.writeLock().lock();
		try {
			String keyItemPurchase = "" + itemPurchase.getCustomerId() + itemPurchase.getItemId()
					+ itemPurchase.getOrderId();
			itemPurchaseMap.put(keyItemPurchase, itemPurchase);
		} finally {
			itemPurchaseLock.writeLock().unlock();
		}
	}

	@Override
	public void removeItemPurchase(int orderId, int customerId, int itemId)
			throws InexistentItemPurchaseException, InventoryManagerException {
		itemPurchaseLock.writeLock().lock();
		try {
			String keyItemPurchase = ""  + customerId + itemId + orderId;
			itemPurchaseMap.remove(keyItemPurchase);
		} finally {
			itemPurchaseLock.writeLock().unlock();
		}
	}

	public boolean hasItemPurchase(ItemPurchase itemPurchase) throws InventoryManagerException {
		itemPurchaseLock.readLock().lock();
		try {
			return itemPurchaseMap.containsValue(itemPurchase);
		} finally {
			itemPurchaseLock.readLock().unlock();
		}
	}
}
