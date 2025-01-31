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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerActionService {

	private final CustomerActionRepository customerActionRepository;
	private final ProductService productService;
	private final ProductRepository productRepository;
	private final SalesTransactionDataRepository salesTransactionDataRepository;

	public CustomerActionService(CustomerActionRepository customerActionRepository,
								 ProductService productService,
								 ProductRepository productRepository,
								 SalesTransactionDataRepository salesTransactionDataRepository) {
		this.customerActionRepository = customerActionRepository;
		this.productService = productService;
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
			case PRODUCT_VIEWED, PRODUCT_ADDED_TO_CART -> {
				var possibleProduct = productService.getProductById(customerAction.productId());
				if (possibleProduct.isEmpty()) {
					throw new UnsupportedOperationException("Provided product id does not map to any DB entry");
				}
				actionToSave.productId(possibleProduct.get().getProductId());
			}

			case CHECKOUT_STARTED -> {
				//Validate if customer has added any product to cart so we can log checkout
				var previousAction = customerActionRepository.findCustomerActionsByCustomerId(customerAction.customerId());
				if (previousAction.isEmpty() || previousAction.stream().noneMatch(it -> it.getActionType().equals(ActionType.PRODUCT_ADDED_TO_CART))) {
					throw new UnsupportedOperationException("Customer does not have any logged action `PRODUCT_ADDED_TO_CART, can't log `CHECKOUT_STARTED` action");
				}
			}
			case PURCHASE_COMPLETED -> {
				//Validate if customer has started checkout so we can log purchase completed
				var previousAction = customerActionRepository.findCustomerActionsByCustomerId(customerAction.customerId());
				if (previousAction.isEmpty() || previousAction.stream().noneMatch(it -> it.getActionType().equals(ActionType.CHECKOUT_STARTED))) {
					throw new UnsupportedOperationException("Customer does not have any logged action `CHECKOUT_STARTED, can't log `PURCHASE_COMPLETED` action");
				}

				if (customerAction.salesTransactionData() == null) {
					//We assume that if sales transaction data object is present, all child objects and data will be present also
					//This needs more elaborate validation in future
					throw new UnsupportedOperationException("Can not log purchase completed because we are missing sales transaction data");
				}

				var salesTransaction = SalesTransactionEntity.builder()
						.totalAmount(customerAction.salesTransactionData().totalAmount())
						.soldProducts(new HashSet<>())
						.build();
				//Map sold products to products stored in DB
				customerAction.salesTransactionData().soldProducts().forEach(productDTO -> productService
						.getProductById(productDTO.productId()).ifPresentOrElse(
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
											.productPrice(1)
											.priceCurrency(Currency.EUR)
											.build();
									productRepository.save(newProduct);
									salesTransaction.addSoldProduct(SoldProductsEntity.builder()
											.quantity(productDTO.quantity())
											.product(newProduct)
											.build());
								}));
				//Save sales transaction data and attach it to main action entity
				salesTransactionDataRepository.save(salesTransaction);
				actionToSave.salesTransaction(salesTransaction);
			}
		}
		customerActionRepository.save(actionToSave.build());
	}

	public List<CustomerActionEntity> findSalesActionsInPeriod(LocalDateTime periodStart, LocalDateTime periodEnd, ActionType actionType) {
		return customerActionRepository.findSalesActionsInPeriod(periodStart, periodEnd, actionType);
	}

	public List<CustomerActionEntity> findByActionTypeAndProductId(ActionType actionType, UUID productId) {
		return customerActionRepository.findAllByActionTypeAndProductId(actionType, productId);
	}

	public long findCountByActionType(ActionType actionType) {
		return customerActionRepository.countByActionType(actionType);
	}
}
