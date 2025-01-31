package eu.skalaengineering.pluto.web.dto;

import eu.skalaengineering.pluto.enums.ActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerActionDTO(
		ActionType actionType,
		UUID customerId,
		UUID productId,
		UUID sessionId,
		SalesTransactionDTO salesTransactionData
) {
}
