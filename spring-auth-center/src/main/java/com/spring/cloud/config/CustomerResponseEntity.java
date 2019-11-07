package com.spring.cloud.config;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.MultiValueMap;

public class CustomerResponseEntity<T extends OAuth2Exception> extends ResponseEntity {

    public CustomerResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }
}
