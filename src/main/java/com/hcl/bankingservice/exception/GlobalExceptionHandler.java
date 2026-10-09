package com.hcl.bankingservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(CustomerIdNotFoundException.class)
public ResponseEntity<ProblemDetail> handleCustomerIdNotFoundException(CustomerIdNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                                                                             exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                             .build();
    }

    @ExceptionHandler(AccountIdNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleAccountIdNotFoundException(AccountIdNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(CreditCardIdNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCreditCardIdNotFoundException(CreditCardIdNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(DebitCardIdNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleDebitCardIdNotFoundException(DebitCardIdNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(TransactionIdNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleTransactionIdNotFoundException(TransactionIdNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCUserIdNotFoundException(UserIdNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        for (final FieldError fieldError : exception.getFieldErrors()) {
            problemDetail.setProperty(fieldError.getField(), fieldError.getRejectedValue());
        }
        return ResponseEntity.of(problemDetail)
                .build();
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
//            MethodArgumentNotValidException exception) {
//        LOGGER.error(exception.getMessage(), exception);
//        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//
//        for (final FieldError fieldError : exception.getFieldErrors()) {
//            problemDetail.setProperty(fieldError.getField(), fieldError.getDefaultMessage());
//        }
//        problemDetail.setStatus(HttpStatus.BAD_REQUEST.value());
//        return ResponseEntity.of(problemDetail)
//                             .build();
////    }

    @ExceptionHandler(NoAccountFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoAccountFoundException(NoAccountFoundException exception){
        LOGGER.error(exception.getMessage(),exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(NoCustomerFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoCustomerFoundException(NoCustomerFoundException exception){
        LOGGER.error(exception.getMessage(),exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }


    @ExceptionHandler(NoDebitCardFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoDebitCardFoundException(NoDebitCardFoundException exception){
        LOGGER.error(exception.getMessage(),exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(NoCreditCardFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoCreditCardFoundException(NoCreditCardFoundException exception){
        LOGGER.error(exception.getMessage(),exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(NoTransactionFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoTransactionFoundException(NoTransactionFoundException exception){
        LOGGER.error(exception.getMessage(),exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(MissingAccountException.class)
    public ResponseEntity<ProblemDetail> handleMissingAccountException(MissingAccountException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(DuplicateAccountTypeException.class)
    public ResponseEntity<ProblemDetail> handleDuplicateAccountTypeException(DuplicateAccountTypeException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ProblemDetail> handleInsufficientBalanceException(InsufficientBalanceException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }

    @ExceptionHandler(NoCardUsedException.class)
    public ResponseEntity<ProblemDetail> handleNoCardUsedException(NoCardUsedException exception) {
        LOGGER.error(exception.getMessage(), exception);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problemDetail.setTitle("Severe Error");
        problemDetail.setProperty("reason", exception.getMessage());
        return ResponseEntity.of(problemDetail)
                .build();
    }
}
