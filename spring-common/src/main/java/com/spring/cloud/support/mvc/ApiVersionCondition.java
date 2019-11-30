package com.spring.cloud.support.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.math.BigDecimal;

/**
 * 版本号规则配置类
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    //如果不传版本号，默认为0
    private static final int DEFAULTVERSION = 0;
    private int apiVersion;

    public ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.getApiVersion());
    }

    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String ver = request.getHeader("Api-Version");
        if (StringUtils.isEmpty(ver)) {
            ver = Integer.toString(DEFAULTVERSION);
        }
        //因为请求头里面传来的是小数，所以需要乘以10
        int version = (int) (Double.valueOf(ver) * 10);
        if (version >= this.apiVersion) // 如果请求的版本号大于等于配置版本号， 则满足
            return this;
        return null;
    }

    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

}
