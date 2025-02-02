package skalaengineering.pluto.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import skalaengineering.pluto.enums.ErrorType;

@Builder
@AllArgsConstructor
public class ErrorModel {
	private String errorCode;
	private String errorMessage;
	private String path;

	@Builder.Default
	private ErrorType errorType = ErrorType.TECHNICAL;
}
