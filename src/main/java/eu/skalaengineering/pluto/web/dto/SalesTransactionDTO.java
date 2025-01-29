package eu.skalaengineering.pluto.web.dto;

import java.util.List;

public record SalesTransactionDTO(
		//This field can probably be calculated form sold products but lets leave it as is for MVP
		double totalAmount,
		List<SoldProductDTO> soldProducts
) {
}
