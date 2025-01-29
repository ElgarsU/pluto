package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import eu.skalaengineering.pluto.db.repository.CustomerActionRepository;
import eu.skalaengineering.pluto.web.dto.CustomerActionDTO;
import org.springframework.stereotype.Service;

@Service
public class CustomerActionService {

	private final CustomerActionRepository customerActionRepository;

	public CustomerActionService(CustomerActionRepository customerActionRepository) {
		this.customerActionRepository = customerActionRepository;
	}

	public void logCustomerAction(CustomerActionDTO customerAction) {

		//Implement request object field validation

		var actionToSave = CustomerActionEntity.builder()
				.actionType(customerAction.actionType())
				.customerId(customerAction.customerId())
				.sessionId(customerAction.sessionId());

		switch (customerAction.actionType()) {
			case PRODUCT_VIEWED, PRODUCT_ADDED_TO_CART -> actionToSave.productId(customerAction.productId());

			case CHECKOUT_STARTED -> {
				var previousAction = customerActionRepository.findCustomerActionByCustomerId(customerAction.customerId());
				if (previousAction.isEmpty()) {
					throw new UnsupportedOperationException()
				}
			}

			case PURCHASE_COMPLETED -> {

			}
		}

		customerActionRepository.save(actionToSave);
	}
}
