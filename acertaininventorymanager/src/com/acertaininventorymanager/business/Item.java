package com.acertaininventorymanager.business;

public class Item {


	/**
	 * The ID of the item.
	 */
	private final int itemId;

	/**
	 * The quantity of the item.
	 */
	private final int quantity;

	/**
	 * The unit price for the item.
	 */
	private final int unitPrice;

	/**
	 * Instantiates a new Item with the item
	 * IDs, along with a quantity ordered and unit price.
	 * 
	 * @param itemId
	 * @param quantity
	 * @param unitPrice
	 */
	public Item(int itemId, int quantity, int unitPrice) {
		this.itemId = itemId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

}
