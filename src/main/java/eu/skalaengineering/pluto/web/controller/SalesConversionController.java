package eu.skalaengineering.pluto.web.controller;

import eu.skalaengineering.pluto.service.SalesConversionService;
import eu.skalaengineering.pluto.web.dto.TotalSalesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static eu.skalaengineering.pluto.config.ApiConstants.SECURE_API_PATH;
import static eu.skalaengineering.pluto.config.ApiConstants.TOTAL_SALES_V1_API_PATH;

@RestController
@RequestMapping(SECURE_API_PATH)
@Tag(name = "Sales conversion API")
public class SalesConversionController {

	private final SalesConversionService salesConversionService;

	public SalesConversionController(SalesConversionService salesConversionService) {
		this.salesConversionService = salesConversionService;
	}

	@GetMapping(TOTAL_SALES_V1_API_PATH)
	@Operation(description = "Returns total sales within a specified date range")
	public ResponseEntity<TotalSalesDTO> totalSalesInPeriod(@Parameter(description = "Start of the period (YYYY-MM-DDTHH:MM:SS)", example = "2025-01-29T00:00:00")
															@RequestParam(defaultValue = "2025-01-29T00:00:00") LocalDateTime periodStart,
															@Parameter(description = "End of the period (YYYY-MM-DDTHH:MM:SS)", example = "2025-01-31T00:00:00")
															@RequestParam(defaultValue = "2025-01-31T00:00:00") LocalDateTime periodEnd) {
		var response = salesConversionService.getTotalSalesInPeriod(periodStart, periodEnd);
		return ResponseEntity.ok(response);
	}
}
