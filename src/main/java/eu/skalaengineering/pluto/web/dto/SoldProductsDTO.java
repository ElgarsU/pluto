package eu.skalaengineering.pluto.web.dto;

import java.util.UUID;

public record SoldProductsDTO(
		UUID productId,
		String quantity
) {
}
