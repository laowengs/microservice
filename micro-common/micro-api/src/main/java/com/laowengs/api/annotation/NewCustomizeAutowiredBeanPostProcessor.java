package com.laowengs.api.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Objects;

@Component
public class NewCustomizeAutowiredBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered, BeanFactoryAware {
    private static final Logger logger = LoggerFactory.getLogger(NewCustomizeAutowiredBeanPostProcessor.class);

    private final Class<? extends Annotation> autowiredAnnotationType = MicroAutowired.class;

    private DefaultListableBeanFactory beanFactory;

    public NewCustomizeAutowiredBeanPostProcessor() {
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        Object proxyInstance = null;
        if (Objects.nonNull(AnnotationUtils.findAnnotation(beanClass, MicroApi.class))) {
            if (beanClass.getInterfaces().length > 0) {
                Object targetObject = null;
                try {
                    targetObject = beanClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                proxyInstance = Proxy.newProxyInstance(beanFactory.getBeanClassLoader(), beanClass.getInterfaces(), new CustomizeInvocationHandler(targetObject));
            }
        }
        return proxyInstance;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return super.postProcessBeforeInitialization(bean, beanName);
    }

//    @Override
//    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
//        //注入
//        Class<?> beanClass = bean.getClass();
//        ReflectionUtils.doWithFields(beanClass, field -> {
//            Object value = null;
//            DependencyDescriptor desc = new DependencyDescriptor(field, true);
//            desc.setContainingClass(bean.getClass());
//            Set<String> autowiredBeanNames = new LinkedHashSet<>(1);
//            Assert.state(beanFactory != null, "No BeanFactory available");
//            TypeConverter typeConverter = beanFactory.getTypeConverter();
//            try {
//                value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);
//            }
//            catch (BeansException ex) {
//                logger.error("异常",ex);
////                throw new UnsatisfiedDependencyException(null, beanName, new InjectionPoint(field), ex);
//            }
//            //Object autowiredBean = beanFactory.getBean(name);
//            if (Objects.nonNull(value)) {
//                ReflectionUtils.makeAccessible(field);
//                field.set(bean, value);
//            }
//        }, field -> Objects.nonNull(field.getAnnotation(autowiredAnnotationType)));
//        return pvs;
//    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}