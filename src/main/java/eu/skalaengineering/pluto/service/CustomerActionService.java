package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import eu.skalaengineering.pluto.db.entity.ProductEntity;
import eu.skalaengineering.pluto.db.entity.SalesTransactionEntity;
import eu.skalaengineering.pluto.db.entity.SoldProductsEntity;
import eu.skalaengineering.pluto.db.repository.CustomerActionRepository;
import eu.skalaengineering.pluto.db.repository.ProductRepository;
import eu.skalaengineering.pluto.db.repository.SalesTransactionDataRepository;
import eu.skalaengineering.pluto.enums.ActionType;
import eu.skalaengineering.pluto.enums.Currency;
import eu.skalaengineering.pluto.web.dto.CustomerActionDTO;
import org.springframework.stereotype.Service;

import java.util.MissingFormatArgumentException;

@Service
public class CustomerActionService {

	private final CustomerActionRepository customerActionRepository;
	private final ProductRepository productRepository;
	private final SalesTransactionDataRepository salesTransactionDataRepository;

	public CustomerActionService(CustomerActionRepository customerActionRepository,
								 ProductRepository productRepository,
								 SalesTransactionDataRepository salesTransactionDataRepository) {
		this.customerActionRepository = customerActionRepository;
		this.productRepository = productRepository;
		this.salesTransactionDataRepository = salesTransactionDataRepository;
	}

	public void logCustomerAction(CustomerActionDTO customerAction) {

		//Implement request object field validation

		//Set initial data that is common to all actions
		var actionToSave = CustomerActionEntity.builder()
				.actionType(customerAction.actionType())
				.customerId(customerAction.customerId())
				.sessionId(customerAction.sessionId());

		switch (customerAction.actionType()) {
			case PRODUCT_VIEWED, PRODUCT_ADDED_TO_CART -> actionToSave.productId(customerAction.productId());

			case CHECKOUT_STARTED -> {
				//Validate if customer has added any product to cart so we can log checkout
				var previousAction = customerActionRepository.findCustomerActionByCustomerId(customerAction.customerId());
				if (previousAction.isEmpty() || previousAction.get().getActionType() != ActionType.PRODUCT_ADDED_TO_CART) {
					throw new UnsupportedOperationException("Customer does not have any valid previous action, can't log checkout started");
				}
			}
			case PURCHASE_COMPLETED -> {
				//Validate if customer has started checkout so we can log purchase completed
				var previousAction = customerActionRepository.findCustomerActionByCustomerId(customerAction.customerId());
				if (previousAction.isEmpty() || previousAction.get().getActionType() != ActionType.CHECKOUT_STARTED) {
					throw new UnsupportedOperationException("Customer does not have any valid previous action, can't log purchase completed");
				}

				if (customerAction.salesTransactionData() == null) {
					//We assume that if sales transaction data object is present, all child objects and data will be present also
					//This needs more elaborate validation in future
					throw new MissingFormatArgumentException("Can not log purchase completed because we are missing sales transaction data");
				}

				var salesTransaction = SalesTransactionEntity.builder()
						.totalAmount(customerAction.salesTransactionData().totalAmount())
						.build();
				//Map sold products to products stored in DB
				customerAction.salesTransactionData().soldProducts().forEach(productDTO -> {
					productRepository
							.findByProductId(productDTO.productId()).ifPresentOrElse(
									//Case where we have found sold product in our DB
									productEntity -> salesTransaction.addSoldProduct(SoldProductsEntity.builder()
											.quantity(productDTO.quantity())
											.product(productEntity)
											.build()),
									//Case where we can't find the product, we create it with generic name and price
									//This is for easier MVP testing only and needs to be refactored
									() -> {
										var newProduct = ProductEntity.builder()
												.productId(productDTO.productId())
												.productName("Generic name")
												.productPrice("1")
												.priceCurrency(Currency.EUR)
												.build();
										productRepository.save(newProduct);
										salesTransaction.addSoldProduct(SoldProductsEntity.builder()
												.quantity(productDTO.quantity())
												.product(newProduct)
												.build());
									});
				});
				//Save sales transaction data and attach it to main action entity
				salesTransactionDataRepository.save(salesTransaction);
				actionToSave.salesTransaction(salesTransaction);
			}
		}
		customerActionRepository.save(actionToSave.build());
	}
}
