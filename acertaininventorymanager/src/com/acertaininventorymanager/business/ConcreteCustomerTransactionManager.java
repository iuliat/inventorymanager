package com.acertaininventorymanager.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.acertaininventorymanager.interfaces.CustomerTransactionManager;
import com.acertaininventorymanager.utils.EmptyRegionException;
import com.acertaininventorymanager.utils.InexistentCustomerException;
import com.acertaininventorymanager.utils.InventoryConstants;
import com.acertaininventorymanager.utils.InventoryManagerException;
import com.acertaininventorymanager.utils.NonPositiveIntegerException;

public class ConcreteCustomerTransactionManager implements CustomerTransactionManager {

	public Map<Integer, ConcreteItemDataManager> itemDataManagerMap;
	public Map<Integer, Customer> costumersMap;
	List<Integer> regionIDsList = new ArrayList<Integer>();
	public final ReadWriteLock customersMapLock = new ReentrantReadWriteLock();

	public ConcreteCustomerTransactionManager() {
		costumersMap = populateCostumersMap();
		itemDataManagerMap = createItemDataManagerInstances();
	}

	private Map<Integer, ConcreteItemDataManager> createItemDataManagerInstances() {
		// TODO Auto-generated method stub
		itemDataManagerMap = new HashMap<Integer, ConcreteItemDataManager>();
		for (int index = 0; index < InventoryConstants.NO_DATA_MANAGERS; index++) {
			itemDataManagerMap.put(index, new ConcreteItemDataManager());
		}
		return itemDataManagerMap;
	}

	private Map<Integer, Customer> populateCostumersMap() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		costumersMap = new HashMap<Integer, Customer>();

		/* Populate data for items */
		for (int index = 0; index < InventoryConstants.NUMBER_CUSTOMERS; index++) {
			int customerID = index;
			int regionID = rand.nextInt(InventoryConstants.NO_REGIONS);
			regionIDsList.add(regionID);
			Customer customer = new Customer(customerID, regionID);
			costumersMap.put(customerID, customer);
		}
		return costumersMap;
	}

	@Override
	public void processOrders(Set<ItemPurchase> itemPurchases) throws InventoryManagerException{
		boolean purchaseFailure = false;
		for (ItemPurchase itemPurchase : itemPurchases) {
			int customerID = itemPurchase.getCustomerId();
			int orderID = itemPurchase.getOrderId();
			int quantity = itemPurchase.getQuantity();
			int price = itemPurchase.getUnitPrice();
			
			if (orderID < 0 || quantity < 0 || price < 0) {
				throw new NonPositiveIntegerException();
			}
			
			if (costumersMap.containsKey(customerID)) {
				if(customerID < 0){
					throw new NonPositiveIntegerException();
				}
			}
			else{
				throw new InexistentCustomerException();
			}
			try {
				int keyItemDataManagerInstance = itemPurchase.hashCode();
				ConcreteItemDataManager allocatedItemDataManagerInstance = itemDataManagerMap
						.get(keyItemDataManagerInstance);
				allocatedItemDataManagerInstance.addItemPurchase(itemPurchase);
			}
			 catch (InventoryManagerException e) {
				// if the add operations crashes, I make sure that all added
				// itemPurchases until that point are
				// removed in order to ensure all-or-nothing semantics
				purchaseFailure = true;
				for (ItemPurchase anItemPurchase : itemPurchases) {
					int keyItemDataManagerInstance = anItemPurchase.hashCode();

					ConcreteItemDataManager allocatedItemDataManagerInstance = itemDataManagerMap
							.get(keyItemDataManagerInstance);

					if (allocatedItemDataManagerInstance.hasItemPurchase(anItemPurchase)) {
						allocatedItemDataManagerInstance.removeItemPurchase(anItemPurchase.getOrderId(),
								anItemPurchase.getCustomerId(), anItemPurchase.getItemId());
					}
				}
				break;
			}
		}
		
		if (!purchaseFailure) {

			try {
				customersMapLock.writeLock().lock();
				for (ItemPurchase itemPurchase : itemPurchases) {
					int customerID = itemPurchase.getCustomerId();
					int quantity = itemPurchase.getQuantity();
					int price = itemPurchase.getUnitPrice();

					// calculate the new bought value for the customer with this
					// itemPurchase
					long newBoughtValue = costumersMap.get(customerID).getValueBought() + quantity * price;
					// update customer's bought value
					costumersMap.get(customerID).setValueBought(newBoughtValue);
					newBoughtValue = 0;
				}
			} finally {
				customersMapLock.writeLock().unlock();
			}
		}
	}
	

	@Override
	public List<RegionTotal> getTotalsForRegions(Set<Integer> regionIds)
			throws NonPositiveIntegerException, EmptyRegionException, InventoryManagerException {

		List<RegionTotal> regionTotalList = new ArrayList<RegionTotal>();

		for (Integer regionID : regionIds) {
			if (regionID < 0) {
				throw new NonPositiveIntegerException();
			}
			if (!regionIDsList.contains(regionID)) {
				throw new EmptyRegionException();
			}
			int totalRegionValue = 0;

			try {
				customersMapLock.writeLock().lock();
				for (Customer customer : costumersMap.values()) {
					if (customer.getRegionId() == regionID) {
						totalRegionValue += customer.getValueBought();
					}
				}
			} finally {
				customersMapLock.writeLock().unlock();
			}
			regionTotalList.add(new RegionTotal(regionID, totalRegionValue));
		}
		return regionTotalList;

	}

}
