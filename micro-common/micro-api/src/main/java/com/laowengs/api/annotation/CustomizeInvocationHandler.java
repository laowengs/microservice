package com.laowengs.api.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomizeInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeInvocationHandler.class);

    private final  Object targetObject;

    public CustomizeInvocationHandler(Object targetObject) {
        this.targetObject = targetObject;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("代理对象开始调用");
        Object invoke  = method.invoke(targetObject,args);
        logger.info("代理对象结束调用");
        return invoke;
    }

}