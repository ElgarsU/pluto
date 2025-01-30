package eu.skalaengineering.pluto.config;

public class ApiConstants {

	private ApiConstants() {
	}

	public static final String SECURE_API_PATH = "/secure";
	public static final String API_VERSION_V1 = "/v1";

	public static final String SECURE_V1_API = SECURE_API_PATH + API_VERSION_V1;

	public static final String PRODUCT_API_PATH = "/products";
	public static final String CREATE_PRODUCT_V1_API_PATH = SECURE_V1_API + PRODUCT_API_PATH + "/create";
	public static final String GET_ALL_PRODUCTS_V1_API_PATH = SECURE_V1_API + PRODUCT_API_PATH + "/get-all";

	public static final String CUSTOMER_ACTION_API_PATH = "/customer-actions";
	public static final String LOG_CUSTOMER_ACTION_V1_API_PATH = SECURE_API_PATH + API_VERSION_V1 + CUSTOMER_ACTION_API_PATH + "/log";
}
