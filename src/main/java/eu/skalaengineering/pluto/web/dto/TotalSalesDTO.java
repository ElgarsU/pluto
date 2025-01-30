package eu.skalaengineering.pluto.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TotalSalesDTO(
		double totalSales,
		LocalDateTime periodStart,
		LocalDateTime periodEnd
) {
}
