package eu.skalaengineering.pluto.web.dto;

import java.util.List;

public record SalesTransactionDTO(
		String totalAmount,
		List<SoldProductsDTO> soldProducts
) {
}
