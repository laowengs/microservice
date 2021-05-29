package com.laowengs.api.annotation;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomizeAutowiredBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor {
    public CustomizeAutowiredBeanPostProcessor() {
        setAutowiredAnnotationType(MicroAutowired.class);
    }
}