package com.voxloud.provisioning.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException exp) {
        logError("Resource Not Found", request, exp);
        return createErrorResponse(exp.getCode(), exp.getFeature(), exp.getReason());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConversionException.class)
    @ResponseBody
    public ErrorResponse handleConversionException(HttpServletRequest request, ConversionException exp) {
        logError("Conversion Exception", request, exp);
        return createErrorResponse(exp.getCode(), exp.getFeature(), exp.getReason());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleMethodArgumentException(HttpServletRequest request, MethodArgumentNotValidException exp) {
        logError("Validation Error", request, exp);
        String errorMessage = extractValidationErrors(exp.getBindingResult());
        return createErrorResponse("", "UNKNOWN", errorMessage);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleGenericException(HttpServletRequest request, Exception exp) {
        logError("Generic Exception", request, exp);
        return createErrorResponse("UNKNOWN", "GENERIC", exp.getMessage());
    }

    private void logError(String message, HttpServletRequest request, Exception exp) {
        log.error("{} at [{}]: {}", message, request.getRequestURI(), exp.getMessage(), exp);
    }

    private String extractValidationErrors(BindingResult bindingResult) {
        return bindingResult.getGlobalErrors().stream()
                .map(this::formatObjectError)
                .collect(Collectors.joining(" "));
    }

    private String formatObjectError(ObjectError error) {
        return String.format("%s : [%s]", error.getObjectName(), error.getDefaultMessage());
    }

    private ErrorResponse createErrorResponse(String code, String feature, String reason) {
        return ErrorResponse.builder()
                .code(code)
                .feature(feature)
                .message(reason)
                .build();
    }
}

