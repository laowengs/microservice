package com.laowengs.eureka.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;

@Controller
@RequestMapping("log")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    protected static final Comparator<LoggerConfiguration> CONFIGURATION_COMPARATOR = new LoggerConfigurationComparator(
            "ROOT");

    @RequestMapping("/level")
    public void demo(String name,String level){

    }



}
class LoggerConfigurationComparator implements Comparator<LoggerConfiguration> {

    private final String rootLoggerName;

    /**
     * @param rootLoggerName the name of the "root" logger
     */
    LoggerConfigurationComparator(String rootLoggerName) {
        Assert.notNull(rootLoggerName, "RootLoggerName must not be null");
        this.rootLoggerName = rootLoggerName;
    }

    @Override
    public int compare(LoggerConfiguration o1, LoggerConfiguration o2) {
        if (this.rootLoggerName.equals(o1.getName())) {
            return -1;
        }
        if (this.rootLoggerName.equals(o2.getName())) {
            return 1;
        }
        return o1.getName().compareTo(o2.getName());
    }

}
