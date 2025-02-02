package skalaengineering.pluto.web.dto;

import lombok.Builder;
import skalaengineering.pluto.enums.Currency;

import java.util.UUID;

@Builder
public record ProductDTO(
		UUID productId,
		String productName,
		double productPrice,
		Currency priceCurrency
) {
}
