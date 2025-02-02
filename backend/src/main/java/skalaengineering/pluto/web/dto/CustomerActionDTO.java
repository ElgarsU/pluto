package skalaengineering.pluto.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import skalaengineering.pluto.enums.ActionType;

import java.util.UUID;

@Builder
public record CustomerActionDTO(
		ActionType actionType,
		UUID customerId,

		@Schema(description = "Optional for non product related actions")
		UUID productId,

		@Schema(description = "Optional for tracking multi step actions in a single session")
		UUID sessionId,

		@Schema(description = "Needs to be provided only when logging `PURCHASE_COMPLETED` action")
		SalesTransactionDTO salesTransactionData
) {
}
