package eu.skalaengineering.pluto.config;

public class ApiConstants {

	private ApiConstants() {
	}

	public static final String SECURE_API_PATH = "/secure";
	public static final String API_VERSION_V1 = "/v1";
	
	public static final String PRODUCT_API_PATH = "/products";
	public static final String CREATE_PRODUCT_V1_API_PATH = API_VERSION_V1 + PRODUCT_API_PATH + "/create";
	public static final String GET_ALL_PRODUCTS_V1_API_PATH = API_VERSION_V1 + PRODUCT_API_PATH + "/get-all";

	public static final String CUSTOMER_ACTION_API_PATH = "/customer-actions";
	public static final String LOG_CUSTOMER_ACTION_V1_API_PATH = API_VERSION_V1 + CUSTOMER_ACTION_API_PATH + "/log";

	public static final String CONVERSION_API_PATH = "/conversion";
	public static final String TOTAL_SALES_V1_API_PATH = API_VERSION_V1 + CONVERSION_API_PATH + "/total-sales";
	public static final String CONVERSION_RATE_V1_API_PATH = API_VERSION_V1 + CONVERSION_API_PATH + "/conversion-rate";


}
