package eu.skalaengineering.pluto.config;

import eu.skalaengineering.pluto.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ErrorModel {
	private String errorCode;
	private String errorMessage;
	private String path;

	@Builder.Default
	private ErrorType errorType = ErrorType.TECHNICAL;
}
