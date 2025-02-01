package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import eu.skalaengineering.pluto.db.repository.CustomerActionRepository;
import eu.skalaengineering.pluto.enums.ActionType;
import eu.skalaengineering.pluto.enums.Currency;
import eu.skalaengineering.pluto.web.dto.CustomerActionDTO;
import eu.skalaengineering.pluto.web.dto.ProductDTO;
import eu.skalaengineering.pluto.web.dto.SalesTransactionDTO;
import eu.skalaengineering.pluto.web.dto.SoldProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CustomerActionServiceTest {

	@Autowired
	private CustomerActionService customerActionService;

	@Autowired
	private CustomerActionRepository customerActionRepository;

	@Autowired
	private ProductService productService;

	private UUID productId;
	private UUID customerId;
	private UUID sessionId;

	@BeforeEach
	void setUp() {
		productId = UUID.randomUUID();
		customerId = UUID.randomUUID();
		sessionId = UUID.randomUUID();

		productService.saveProduct(ProductDTO.builder()
				.productId(productId)
				.productName("Test Product")
				.productPrice(10)
				.priceCurrency(Currency.USD)
				.build());
	}

	@Test
	void shouldLogProductViewedAction() {
		// Setup
		var customerAction = createCustomerAction(ActionType.PRODUCT_VIEWED, null);

		// Setup
		customerActionService.logCustomerAction(customerAction);

		// Assert
		List<CustomerActionEntity> actions = customerActionRepository.findAll();
		assertEquals(7, actions.size());
		assertEquals(ActionType.PRODUCT_VIEWED, actions.get(0).getActionType());
		assertThat(actions.get(0).getProductId()).isNotNull();
	}

	@Test
	void shouldLogProductAddedToCartAction() {
		// Setup
		var customerAction = createCustomerAction(ActionType.PRODUCT_ADDED_TO_CART, null);

		// Setup
		customerActionService.logCustomerAction(customerAction);

		// Assert
		List<CustomerActionEntity> actions = customerActionRepository.findAll();
		assertEquals(7, actions.size());
		assertEquals(ActionType.PRODUCT_ADDED_TO_CART, actions.get(6).getActionType());
		System.out.println(actions.get(6).getProductId());
		assertThat(actions.get(6).getProductId()).isNotNull();
	}

	@Test
	void shouldLogCheckoutStartedAction() {
		// Setup
		customerActionService.logCustomerAction(createCustomerAction(ActionType.PRODUCT_ADDED_TO_CART, null));

		// Setup
		customerActionService.logCustomerAction(createCustomerAction(ActionType.CHECKOUT_STARTED, null));

		// Assert
		List<CustomerActionEntity> actions = customerActionRepository.findAll();
		assertEquals(8, actions.size());
		assertEquals(ActionType.CHECKOUT_STARTED, actions.get(7).getActionType());
	}

	@Test
	void shouldLogPurchaseCompletedAction() {
		// Setup
		customerActionService.logCustomerAction(createCustomerAction(ActionType.PRODUCT_ADDED_TO_CART, null));
		customerActionService.logCustomerAction(createCustomerAction(ActionType.CHECKOUT_STARTED, null));
		var salesTransactionData = SalesTransactionDTO.builder()
				.totalAmount(100.00)
				.soldProducts(List.of(SoldProductDTO.builder()
						.productId(productId)
						.quantity(2L)
						.build()))
				.build();

		// Perform
		customerActionService.logCustomerAction(createCustomerAction(ActionType.PURCHASE_COMPLETED, salesTransactionData));

		// Assert
		List<CustomerActionEntity> actions = customerActionRepository.findAll();
		assertEquals(9, actions.size());
		assertEquals(ActionType.PURCHASE_COMPLETED, actions.get(8).getActionType());
		assertNotNull(actions.get(8).getSalesTransaction());
		assertEquals(100.00, actions.get(8).getSalesTransaction().getTotalAmount());
		assertEquals(1, actions.get(8).getSalesTransaction().getSoldProducts().size());
	}


	@Test
	void shouldFailToLogCheckoutStartedWithoutPreviousAction() {
		// Setup
		var customerAction = createCustomerAction(ActionType.CHECKOUT_STARTED, null);

		// Perform & Assert
		UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
				() -> customerActionService.logCustomerAction(customerAction));
		assertEquals("Customer does not have any logged action `PRODUCT_ADDED_TO_CART, can't log `CHECKOUT_STARTED` action", exception.getMessage());
	}

	@Test
	void shouldFailToLogPurchaseCompletedWithoutValidSalesData() {
		// Setup
		customerActionService.logCustomerAction(createCustomerAction(ActionType.PRODUCT_ADDED_TO_CART, null));
		customerActionService.logCustomerAction(createCustomerAction(ActionType.CHECKOUT_STARTED, null));
		var customerAction = createCustomerAction(ActionType.PURCHASE_COMPLETED, null);

		// Perform & Assert
		UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
				() -> customerActionService.logCustomerAction(customerAction));
		assertEquals("Can not log purchase completed because we are missing sales transaction data", exception.getMessage());
	}


	//	Helper methods
	private CustomerActionDTO createCustomerAction(ActionType actionType, SalesTransactionDTO salesTransactionData) {
		return CustomerActionDTO.builder()
				.actionType(actionType)
				.customerId(customerId)
				.sessionId(sessionId)
				.productId(actionType == ActionType.PRODUCT_VIEWED || actionType == ActionType.PRODUCT_ADDED_TO_CART
						? productId : null)
				.salesTransactionData(salesTransactionData)
				.build();
	}
}
