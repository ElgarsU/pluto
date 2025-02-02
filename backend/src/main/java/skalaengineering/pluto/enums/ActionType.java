package skalaengineering.pluto.enums;

public enum ActionType {
	PRODUCT_VIEWED,
	PRODUCT_ADDED_TO_CART,
	CHECKOUT_STARTED,
	PURCHASE_COMPLETED;

	public static ActionType mapToActionType(ConversionType conversionType) {
		return switch (conversionType) {
			case VIEWED_PURCHASED -> ActionType.PRODUCT_VIEWED;
			case ADDED_TO_CART_PURCHASED -> ActionType.PRODUCT_ADDED_TO_CART;
			case CHECKOUT_PURCHASED -> ActionType.CHECKOUT_STARTED;
		};
	}
}
