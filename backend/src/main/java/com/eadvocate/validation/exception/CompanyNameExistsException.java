package com.eadvocate.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Company with that name already exist !!")
public class CompanyNameExistsException extends RuntimeException {


}
