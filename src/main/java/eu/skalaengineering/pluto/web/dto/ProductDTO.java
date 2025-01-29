package eu.skalaengineering.pluto.web.dto;

import eu.skalaengineering.pluto.enums.Currency;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductDTO(
		UUID productId,
		String productName,
		double productPrice,
		Currency priceCurrency
) {
}
