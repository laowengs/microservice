package com.laowengs.api.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class CustomizeBeanFactoryProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {



    private final AnnotationBeanNameGenerator generator=new AnnotationBeanNameGenerator();

    private final Set<BeanDefinitionHolder> beanDefinitionsSet = new LinkedHashSet<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory){
//            this.beanFactory= (DefaultListableBeanFactory) beanFactory;
            ClassPathBeanDefinitionScanner scanner= new ClassPathBeanDefinitionScanner(beanFactory);
            scanner.resetFilters(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(MicroApi.class));
            Set<BeanDefinition> beanDefinitionSet = scanner.findCandidateComponents("com.laowengs");
            for (BeanDefinition beanDefinition : beanDefinitionSet) {
                Class<?> clazz = ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), ((DefaultListableBeanFactory) beanFactory).getBeanClassLoader());
                MicroApi annotation = AnnotationUtils.findAnnotation(clazz, MicroApi.class);
                if (Objects.nonNull(annotation)) {
                    String beanName = generator.generateBeanName(beanDefinition, beanFactory);
                    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(beanDefinition, beanName);
                    beanDefinitionsSet.add(definitionHolder);
                    BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanFactory);
                }
            }
        }else{
//            log.info("没有指定类型的bean工厂：DefaultListableBeanFactory");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}