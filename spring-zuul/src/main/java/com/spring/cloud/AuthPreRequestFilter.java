package com.spring.cloud;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.spring.cloud.utils.SecurityUtils;
import com.spring.cloud.utils.RequestUserUtils;
import com.spring.cloud.security.SecurityUser;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 统一权限认证
 */
public class AuthPreRequestFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.err.println(request.getRequestURI());
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        SecurityUser user = SecurityUtils.currentUser();
        if (user != null) {
            currentContext.addZuulRequestHeader(RequestUserUtils.USERHEADERKEY, JSONObject.toJSON(user).toString());
        }
        return null;
    }
}
