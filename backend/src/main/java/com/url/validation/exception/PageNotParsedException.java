package com.url.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Page content can not be parsed !!")
public class PageNotParsedException extends RuntimeException {


}
