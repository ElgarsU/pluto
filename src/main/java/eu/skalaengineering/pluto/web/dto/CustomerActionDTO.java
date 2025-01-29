package eu.skalaengineering.pluto.web.dto;

import eu.skalaengineering.pluto.enums.ActionType;

import java.util.UUID;

public record CustomerActionDTO(
		ActionType actionType,
		UUID customerId,
		UUID productId,
		UUID sessionId,
		SalesTransactionDTO salesTransactionData
) {
}
