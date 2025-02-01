package eu.skalaengineering.pluto.web.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SalesTransactionDTO(
		//This field can probably be calculated form sold products but let's leave it as is for MVP
		double totalAmount,
		List<SoldProductDTO> soldProducts
) {
}
