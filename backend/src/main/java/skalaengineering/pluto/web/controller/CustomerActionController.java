package skalaengineering.pluto.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skalaengineering.pluto.service.CustomerActionService;
import skalaengineering.pluto.web.dto.CustomerActionDTO;

import static skalaengineering.pluto.config.ApiConstants.LOG_CUSTOMER_ACTION_V1_API_PATH;
import static skalaengineering.pluto.config.ApiConstants.SECURE_API_PATH;

@RestController
@RequestMapping(SECURE_API_PATH)
@Tag(name = "Customer action API")
public class CustomerActionController {

	private final CustomerActionService customerActionService;

	public CustomerActionController(CustomerActionService customerActionService) {
		this.customerActionService = customerActionService;
	}

	@PostMapping(LOG_CUSTOMER_ACTION_V1_API_PATH)
	@Operation(description = "Logs the received customer action with provided data")
	public void logCustomerActions(@RequestBody CustomerActionDTO actionDTO) {
		customerActionService.logCustomerAction(actionDTO);
	}
}
