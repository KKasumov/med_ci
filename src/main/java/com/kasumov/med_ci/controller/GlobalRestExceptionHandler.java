package com.kasumov.med_ci.controller;

import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.OverdueDateException;
import com.kasumov.med_ci.model.exception.OutIntegrationException;
import com.kasumov.med_ci.model.exception.SameConstraintException;
import com.kasumov.med_ci.model.exception.CharacteristicsException;
import com.kasumov.med_ci.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public <T> Response<T> checkEntityExist(EntityNotFoundException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SameConstraintException.class)
    public <T> Response<T> checkSameConstraintExist(SameConstraintException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(CharacteristicsException.class)
    public <T> Response<T> checkCharacteristicsExist(CharacteristicsException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BusyConstraintException.class)
    public <T> Response<T> checkEmailAlreadyExist(BusyConstraintException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(OverdueDateException.class)
    public <T> Response<T> checkDateIsOverdue(OverdueDateException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidParametersPassedException.class)
    public <T> Response<T> checkParametersAreInvalid(InvalidParametersPassedException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(OutIntegrationException.class)
    public <T> Response<T> queryToOutIntegration(OutIntegrationException ex) {
        return Response.error(ex.getCode(), ex.getMessage());
    }
}

