package com.laowengs.api.annotation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

class CustomizeBeanFactoryProcessorTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeBeanFactoryProcessorTest.class);



    @org.junit.jupiter.api.Test
    void test_custom_spring_bean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        TestA bean = applicationContext.getBean(TestA.class);
        bean.say();
        bean.testB.say();
        ITest beanB = (ITest) applicationContext.getBean("testB");
        beanB.say();
    }


}
@Configuration
@ComponentScan("com.laowengs.api.annotation")
//@EnableAspectJAutoProxy
class Config{

}


@MicroApi
class TestA{
    @Autowired
    public ITest testB;


    public String say() {
        System.out.println("TestA");
        return "TestA";
    }
}

@MicroApi
class TestB implements ITest {

    public String say() {
        System.out.println("TestB");
        return "TestA";
    }
}