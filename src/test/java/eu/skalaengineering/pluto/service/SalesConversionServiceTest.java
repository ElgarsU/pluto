package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.enums.ActionType;
import eu.skalaengineering.pluto.enums.ConversionType;
import eu.skalaengineering.pluto.enums.Currency;
import eu.skalaengineering.pluto.web.dto.ConversionRateDTO;
import eu.skalaengineering.pluto.web.dto.CustomerActionDTO;
import eu.skalaengineering.pluto.web.dto.ProductDTO;
import eu.skalaengineering.pluto.web.dto.SalesTransactionDTO;
import eu.skalaengineering.pluto.web.dto.SoldProductDTO;
import eu.skalaengineering.pluto.web.dto.TotalSalesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class SalesConversionServiceTest {

	@Autowired
	private SalesConversionService salesConversionService;

	@Autowired
	private CustomerActionService customerActionService;

	@Autowired
	private ProductService productService;

	private UUID productId;
	private UUID customerId;
	private UUID sessionId;
	private LocalDateTime now;

	@BeforeEach
	void setUp() {
		productId = UUID.randomUUID();
		customerId = UUID.randomUUID();
		sessionId = UUID.randomUUID();
		now = LocalDateTime.now();

		productService.saveProduct(ProductDTO.builder()
				.productId(productId)
				.productName("Test Product")
				.productPrice(10)
				.priceCurrency(Currency.USD)
				.build());
	}

	@Test
	void shouldGetTotalSalesInPeriod() {
		// Setup
		simulateProductAddedToCartActions(1);
		logCheckoutStartedAction();
		logPurchaseCompletedAction(100.0);

		// Perform
		LocalDateTime periodStart = now.minusHours(1);
		LocalDateTime periodEnd = now.plusHours(1);
		TotalSalesDTO totalSales = salesConversionService.getTotalSalesInPeriod(periodStart, periodEnd);

		// Assert
		assertNotNull(totalSales);
		// Total sales amount is 300 because DB has preloaded sales data with amount 200
		assertEquals(300.0, totalSales.totalSales());
		assertEquals(periodStart, totalSales.periodStart());
		assertEquals(periodEnd, totalSales.periodEnd());
	}

	@Test
	void shouldReturnZeroSalesInPeriodWhenNoActions() {
		// Setup
		LocalDateTime periodStart = now.minusHours(10);
		LocalDateTime periodEnd = now.minusHours(5);

		// Perform
		TotalSalesDTO totalSales = salesConversionService.getTotalSalesInPeriod(periodStart, periodEnd);

		// Assert
		assertNotNull(totalSales);
		assertEquals(0.0, totalSales.totalSales());
		assertEquals(periodStart, totalSales.periodStart());
		assertEquals(periodEnd, totalSales.periodEnd());
	}

	@Test
	void shouldCalculateViewedToPurchasedConversionRate() {
		// Setup
		simulateProductViewedActions(5);
		simulateProductAddedToCartActions(1);
		logCheckoutStartedAction();
		logPurchaseCompletedAction(200.0);

		// Perform
		ConversionRateDTO conversionRate = salesConversionService.getConversionRate(ConversionType.VIEWED_PURCHASED, productId);

		// Assert
		assertNotNull(conversionRate);
		assertEquals(ConversionType.VIEWED_PURCHASED, conversionRate.conversionType());
		assertEquals(0.2, conversionRate.conversionRate());
	}

	@Test
	void shouldCalculateAddedToCartToPurchasedConversionRate() {
		// Setup
		simulateProductAddedToCartActions(4);
		logCheckoutStartedAction();
		logPurchaseCompletedAction(150.0);

		// Perform
		ConversionRateDTO conversionRate = salesConversionService.getConversionRate(ConversionType.ADDED_TO_CART_PURCHASED, productId);

		// Assert
		assertNotNull(conversionRate);
		assertEquals(ConversionType.ADDED_TO_CART_PURCHASED, conversionRate.conversionType());
		assertEquals(0.25, conversionRate.conversionRate());
	}

	@Test
	void shouldCalculateCheckoutToPurchasedConversionRate() {
		// Setup
		simulateProductAddedToCartActions(1);
		logCheckoutStartedAction();
		logPurchaseCompletedAction(300.0);

		// Perform
		ConversionRateDTO conversionRate = salesConversionService.getConversionRate(ConversionType.CHECKOUT_PURCHASED, null);

		// Assert
		assertNotNull(conversionRate);
		assertEquals(ConversionType.CHECKOUT_PURCHASED, conversionRate.conversionType());
		assertEquals(0.6666666666666666, conversionRate.conversionRate());
	}

	//	Helper methods
	private void logPurchaseCompletedAction(double totalAmount) {
		var salesTransaction = SalesTransactionDTO.builder()
				.totalAmount(totalAmount)
				.soldProducts(List.of(SoldProductDTO.builder()
						.productId(productId)
						.quantity(1L)
						.build()))
				.build();
		customerActionService.logCustomerAction(CustomerActionDTO.builder()
				.actionType(ActionType.PURCHASE_COMPLETED)
				.customerId(customerId)
				.sessionId(sessionId)
				.salesTransactionData(salesTransaction)
				.build());
	}

	private void logCheckoutStartedAction() {
		customerActionService.logCustomerAction(CustomerActionDTO.builder()
				.actionType(ActionType.CHECKOUT_STARTED)
				.customerId(customerId)
				.sessionId(sessionId)
				.build());
	}

	private void simulateProductViewedActions(int count) {
		for (int i = 0; i < count; i++) {
			customerActionService.logCustomerAction(CustomerActionDTO.builder()
					.actionType(ActionType.PRODUCT_VIEWED)
					.customerId(customerId)
					.sessionId(sessionId)
					.productId(productId)
					.build());
		}
	}

	private void simulateProductAddedToCartActions(int count) {
		for (int i = 0; i < count; i++) {
			customerActionService.logCustomerAction(CustomerActionDTO.builder()
					.actionType(ActionType.PRODUCT_ADDED_TO_CART)
					.customerId(customerId)
					.sessionId(sessionId)
					.productId(productId)
					.build());
		}
	}
}
