package com.laowengs.zuul;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

public class GrayHolder {

    private  static HystrixRequestVariableDefault<String> gray ;
   /* static {
        System.out.println("init holder");
    }*/


    public static String getVersion(){
        return  gray.get();
    }

    public static void setVersion(String version){
        HystrixRequestContext.initializeContext();
        gray =  new HystrixRequestVariableDefault<>();
        gray.set(version);
    }



}