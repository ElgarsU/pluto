package skalaengineering.pluto.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import skalaengineering.pluto.enums.ErrorType;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionAdvice {

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<ErrorModel> handleLogicalExceptions(UnsupportedOperationException e, HttpServletRequest request) {
		ErrorModel error = ErrorModel.builder()
				.errorMessage(e.getMessage())
				.errorCode("BE-001")
				.errorType(ErrorType.LOGICAL)
				.path(request.getRequestURI())
				.build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorModel> handleTechnicalExceptions(Exception e, HttpServletRequest request) {
		ErrorModel error = ErrorModel.builder()
				.errorMessage(e.getMessage())
				.errorCode("TE-001")
				.errorType(ErrorType.TECHNICAL)
				.path(request.getRequestURI())
				.build();
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
