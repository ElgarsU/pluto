package skalaengineering.pluto.web.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SoldProductDTO(
		UUID productId,
		Long quantity
) {
}
