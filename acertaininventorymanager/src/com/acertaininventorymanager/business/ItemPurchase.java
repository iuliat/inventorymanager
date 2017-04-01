package com.acertaininventorymanager.business;

import com.acertaininventorymanager.utils.InventoryConstants;


public class ItemPurchase{

	/**
	 * The ID of the order for this item purchase.
	 */
	private final int orderId;

	/**
	 * The ID of the customer for this item purchase.
	 */
	private final int customerId;

	/**
	 * The ID of the item for this item purchase.
	 */
	private final int itemId;

	/**
	 * The quantity ordered of the item in this item purchase.
	 */
	private final int quantity;

	/**
	 * The unit price agreed for the item in this item purchase.
	 */
	private final int unitPrice;

	/**
	 * Instantiates a new ItemPurchase with the given order, customer, and item
	 * IDs, along with a quantity ordered and unit price.
	 * 
	 * @param orderId
	 * @param customerId
	 * @param itemId
	 * @param quantity
	 * @param unitPrice
	 */
	public ItemPurchase(int orderId, int customerId, int itemId, int quantity, int unitPrice) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return the unitPrice
	 */
	public int getUnitPrice() {
		return unitPrice;
	}


	// TODO: include here code for hashCode and equals as required by your
	// implementation. Note that an item purchase is identified by the
	// combination of orderId, customerId, and itemId
	
	@Override
	public int hashCode() {
		final int prime = 17;
		int result = 1;
		result = prime * result + customerId;
		result = prime * result + itemId;
		result = prime * result + orderId;
		return result % InventoryConstants.NO_DATA_MANAGERS;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPurchase other = (ItemPurchase) obj;
		if (customerId != other.customerId)
			return false;
		if (itemId != other.itemId)
			return false;
		if (orderId != other.orderId)
			return false;
		return true;
	}


}
