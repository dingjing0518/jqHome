package com.by.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.MemberNotValidException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotFoundException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;

@ControllerAdvice
public class ExceptionController {
    Logger log = LoggerFactory.getLogger(ExceptionController.class);
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<Fail> notFound() {
        return new ResponseEntity<Fail>(new Fail("not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStorageException.class)
    @ResponseBody
    public ResponseEntity<Fail> notEnoughCouponException() {
        return new ResponseEntity<Fail>(new Fail(getMessage("coupon.OutOfStorageException")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidException.class)
    @ResponseBody
    public ResponseEntity<Fail> notValidException() {
        return new ResponseEntity<Fail>(new Fail(getMessage("coupon.NotValidException")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotValidException.class)
    @ResponseBody
    public ResponseEntity<Fail> MemberNotValidException() {
        return new ResponseEntity<Fail>(new Fail(getMessage("member.NotValidException")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughScoreException.class)
    @ResponseBody
    public ResponseEntity<Fail> notEnoughScoreException() {
        return new ResponseEntity<Fail>(new Fail(getMessage("coupon.NotEnoughScoreException")), HttpStatus.BAD_REQUEST);
    }

    /*
     * @ExceptionHandler(value = { Exception.class, RuntimeException.class })
     *
     * @ResponseBody public ResponseEntity<Fail>
     * defaultErrorHandler(HttpServletRequest request, Exception e) {
     * log.error(null, request.getParameterMap()); return new
     * ResponseEntity<Fail>(new Fail("system fail"), HttpStatus.NOT_FOUND); }
     */
    private String getMessage(String code) {
        return messageSource.getMessage(code, new Object[]{}, Locale.CHINA);
    }
}
