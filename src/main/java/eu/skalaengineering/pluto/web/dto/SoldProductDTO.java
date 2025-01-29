package eu.skalaengineering.pluto.web.dto;

import java.util.UUID;

public record SoldProductDTO(
		UUID productId,
		Long quantity
) {
}
