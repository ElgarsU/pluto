package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.db.repository.CustomerActionRepository;
import eu.skalaengineering.pluto.enums.ActionType;
import eu.skalaengineering.pluto.web.dto.TotalSalesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SalesConversionService {

	private final CustomerActionRepository customerActionRepository;

	public SalesConversionService(CustomerActionRepository customerActionRepository) {
		this.customerActionRepository = customerActionRepository;
	}

	public TotalSalesDTO getTotalSalesInPeriod(LocalDateTime periodStart, LocalDateTime periodEnd) {
		log.error("period start: {}", periodStart);
		log.error("period end: {}", periodEnd);
		var salesActionsInPeriod = customerActionRepository.findSalesActionsInPeriod(periodStart, periodEnd, ActionType.PURCHASE_COMPLETED);
		var totalSales = salesActionsInPeriod.stream()
				.map(salesAction -> salesAction.getSalesTransaction().getTotalAmount())
				.reduce(0.0, Double::sum);

		return TotalSalesDTO.builder()
				.totalSales(totalSales)
				.periodStart(periodStart)
				.periodEnd(periodEnd)
				.build();
	}
}
