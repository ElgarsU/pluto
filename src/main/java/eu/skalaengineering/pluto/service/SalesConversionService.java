package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import eu.skalaengineering.pluto.db.entity.ProductEntity;
import eu.skalaengineering.pluto.db.entity.SoldProductsEntity;
import eu.skalaengineering.pluto.db.repository.SoldProductRepository;
import eu.skalaengineering.pluto.enums.ActionType;
import eu.skalaengineering.pluto.enums.ConversionType;
import eu.skalaengineering.pluto.web.dto.ConversionRateDTO;
import eu.skalaengineering.pluto.web.dto.TotalSalesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SalesConversionService {

	private final ProductService productService;
	private final CustomerActionService customerActionService;
	private final SoldProductRepository soldProductRepository;

	public SalesConversionService(ProductService productService,
								  CustomerActionService customerActionService,
								  SoldProductRepository soldProductRepository) {
		this.productService = productService;
		this.customerActionService = customerActionService;
		this.soldProductRepository = soldProductRepository;
	}

	public TotalSalesDTO getTotalSalesInPeriod(LocalDateTime periodStart, LocalDateTime periodEnd) {
		var salesActionsInPeriod = customerActionService.findSalesActionsInPeriod(periodStart, periodEnd, ActionType.PURCHASE_COMPLETED);
		var totalSales = salesActionsInPeriod.stream()
				.map(salesAction -> salesAction.getSalesTransaction().getTotalAmount())
				.reduce(0.0, Double::sum);

		return TotalSalesDTO.builder()
				.totalSales(totalSales)
				.periodStart(periodStart)
				.periodEnd(periodEnd)
				.build();
	}

	public ConversionRateDTO getConversionRate(ConversionType conversionType, UUID productId) {
		return switch (conversionType) {
			case VIEWED_PURCHASED -> calculateConversionByTypeAndProduct(ConversionType.VIEWED_PURCHASED, productId);

			case ADDED_TO_CART_PURCHASED -> calculateConversionByTypeAndProduct(ConversionType.ADDED_TO_CART_PURCHASED, productId);

			case CHECKOUT_PURCHASED -> calculateCheckoutPurchasedConversionRate();
		};
	}

	private ConversionRateDTO calculateConversionByTypeAndProduct(ConversionType conversionType, UUID productId) {
		Optional<ProductEntity> product = productService.getProductById(productId);

		if (product.isEmpty()) {
			log.error("Product with id {} not found", productId);
			return null;
		}

		ActionType actionType = ActionType.mapToActionType(conversionType);

		List<CustomerActionEntity> addedToCartProducts = customerActionService.findByActionTypeAndProductId(actionType, product.get().getProductId());

		if (addedToCartProducts.isEmpty()) {
			log.error("Product named: {} has not been added to cart, can't calculate conversion rate", product.get().getProductName());
			return null;
		}

		List<SoldProductsEntity> soldProducts = soldProductRepository.findAllByProduct(product.get());

		return ConversionRateDTO.builder()
				.conversionType(conversionType)
				.productName(product.get().getProductName())
				.productId(productId)
				.conversionRate((double) soldProducts.size() / addedToCartProducts.size())
				.build();
	}


	private ConversionRateDTO calculateCheckoutPurchasedConversionRate() {
		var checkoutActions = customerActionService.findCountByActionType(ActionType.CHECKOUT_STARTED);
		var purchaseActions = customerActionService.findCountByActionType(ActionType.PURCHASE_COMPLETED);
		if (checkoutActions == 0) {
			log.error("no checkout actions performed");
			return null;
		}
		return ConversionRateDTO.builder()
				.conversionType(ConversionType.CHECKOUT_PURCHASED)
				.conversionRate((double) purchaseActions / checkoutActions)
				.build();
	}
}
