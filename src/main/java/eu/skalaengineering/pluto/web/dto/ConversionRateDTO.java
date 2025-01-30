package eu.skalaengineering.pluto.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.skalaengineering.pluto.enums.ConversionType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ConversionRateDTO(
		ConversionType conversionType,

		//Will not be present for ConversionType.CHECKOUT_PURCHASED
		@JsonInclude(JsonInclude.Include.NON_NULL)
		UUID productId,

		//Will not be present for ConversionType.CHECKOUT_PURCHASED
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String productName,
		double conversionRate
) {
}
