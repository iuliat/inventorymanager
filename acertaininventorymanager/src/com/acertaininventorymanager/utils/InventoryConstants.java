package com.acertaininventorymanager.utils;

/**
 * InventoryConstants declares the constants used in the CertainInventoryManager (by
 * both servers and clients).
 */

public final class InventoryConstants {


	/** The Constant NUMBER_ITEMS. 
	 *  The item map is populated with NUMBER_ITEMS of products.
	 *  The item unique ID, quantity and price are randomly generated.
	 *   */
	public static final int NUMBER_ITEMS = 10;
	
	/**
	 *  Items are randomly generated.
	 *  All generated items will have the default quantity ITEM_QUANTITY
	 */
	public static final int ITEM_QUANTITY = 1000;
	
	/**
	 *  Items are randomly generated.
	 *  Must be ensured that the IDs range is bigger that number of items.
	 *  Item unique ID can range between 0 and ITEM_ID_RANGE
	 */
	public static final int ITEM_ID_RANGE = 10*NUMBER_ITEMS;
		
	/**
	 *  Items are randomly generated.
	 *  Item's price can range between 0 and ITEM_PRICE_RANGE
	 */
	public static final int ITEM_PRICE_RANGE = 10000;

	public static final int NUMBER_CUSTOMERS = 100;

	public static final int NO_REGIONS = 50;

	public static final String PROPERTY_KEY_SERVER_PORT = "port";

	public static final boolean BINARY_SERIALIZATION = true;

	/** The Constant CLIENT_MAX_CONNECTION_ADDRESS. */
	public static final int CLIENT_MAX_CONNECTION_ADDRESS = 200;

	/** The Constant CLIENT_MAX_THREADSPOOL_THREADS. */
	public static final int CLIENT_MAX_THREADSPOOL_THREADS = 250;

	/** The Constant CLIENT_MAX_TIMEOUT_MILLISECS. */
	public static final int CLIENT_MAX_TIMEOUT_MILLISECS = 30000;

	/** The Constant strERR_CLIENT_REQUEST_SENDING. */
	public static final String STR_ERR_CLIENT_REQUEST_SENDING = "ERR_CLIENT_REQUEST_SENDING";

	/** The Constant strERR_CLIENT_REQUEST_EXCEPTION. */
	public static final String STR_ERR_CLIENT_REQUEST_EXCEPTION = "ERR_CLIENT_REQUEST_EXCEPTION";

	/** The Constant strERR_CLIENT_REQUEST_TIMEOUT. */
	public static final String STR_ERR_CLIENT_REQUEST_TIMEOUT = "CLIENT_REQUEST_TIMEOUT";

	/** The Constant strERR_CLIENT_RESPONSE_DECODING. */
	public static final String STR_ERR_CLIENT_RESPONSE_DECODING = "CLIENT_RESPONSE_DECODING";

	/** The Constant strERR_CLIENT_UNKNOWN. */
	public static final String STR_ERR_CLIENT_UNKNOWN = "CLIENT_UNKNOWN";

	/** The Constant strERR_CLIENT_ENCODING. */
	public static final String STR_ERR_CLIENT_ENCODING = "CLIENT_ENCODING";

	public static final String PROPERTY_KEY_LOCAL_TEST = "localtest";
	
	public static int NO_DATA_MANAGERS = 10;
}