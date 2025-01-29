package eu.skalaengineering.pluto.config;

public class ApiConstants {

	private ApiConstants() {
	}

	public static final String SECURE_API_PATH = "/secure";
	public static final String API_VERSION_V1 = "v1";

	public static final String PRODUCT_API_PATH = "/products";
	public static final String CREATE_PRODUCT_V1_API_PATH = SECURE_API_PATH + API_VERSION_V1 + PRODUCT_API_PATH + "/create";
}
