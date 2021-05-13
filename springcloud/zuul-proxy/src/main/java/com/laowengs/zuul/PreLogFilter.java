package com.laowengs.zuul;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class PreLogFilter extends ZuulFilter {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String,String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if(headerNames.hasMoreElements()){
            String key = headerNames.nextElement().toLowerCase();
            headerMap.put(key,request.getHeader(key));
        }
        String version = request.getHeader("mir-version");
        if(version == null){
            version = "v1";
        }
        GrayHolder.setVersion(version);
        LOGGER.info("Remote host:{},method:{},uri:{},parameterMap {},header {}", host, method, uri,parameterMap,headerMap);
        return null;
    }
}
