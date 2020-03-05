package com.spring.cloud.base;


import java.io.Serializable;

/**
 * User: chenby
 * Date: 13-10-17
 * Time: 下午3:58
 */
public interface Command<T extends Object> extends Serializable {
    T toDomain();

    Command<T> fromDomain(T domain);
}
