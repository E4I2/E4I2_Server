package io.e4i2.advice;


import io.e4i2.dto.ResponseDTO;
import io.e4i2.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO exceptionHandler(MethodArgumentNotValidException e) {
//
//        BindingResult bindingResult = e.getBindingResult();
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
//
//        fieldErrors.forEach(err -> errorResponse.addValidation(err.getField(), err.getDefaultMessage()));
//
//        return errorResponse;
        log.error("MethodArgumentNotValidException = {}", e.getMessage());
        return new ResponseDTO(new ResponseDTO.Result(400, "잘못된 요청입니다.", "fail"));
        
    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
//        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
//        errorResponse.addValidation("IllegalArgumentException" , "");
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseDTO illegalArgumentExceptionHandler(Exception e) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//        errorResponse.addValidation("serverError = ", errorResponse.getMessage());
//        return errorResponse;
        log.error("Exception = {}", e.getMessage());
        return new ResponseDTO(new ResponseDTO.Result(400, "잘못된 요청입니다.", "fail"));
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDTO jsonExceptionHandler(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException = {}", e.getMessage());
        return new ResponseDTO(new ResponseDTO.Result(400, "잘못된 요청입니다.", "fail"));
    }
}
