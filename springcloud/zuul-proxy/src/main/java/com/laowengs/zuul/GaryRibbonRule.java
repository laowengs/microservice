package com.laowengs.zuul;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class GaryRibbonRule  extends ZoneAvoidanceRule {
    private static final Logger logger = LoggerFactory.getLogger(GaryRibbonRule.class);


    @Override
    public Server choose(Object key) {
        String version = GrayHolder.getVersion();
        logger.debug("get request version {}",version);
        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        Server returnServer = null;
        for (Server server : serverList) {
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
            String metaVersion = metadata.get("version");
            if (metaVersion != null) {
                if (metaVersion.equals(version)) {
                    returnServer = server;
                    break;
                }
            }
        }
        if(returnServer == null){
            logger.info("未获取到符合版本的实例");
            super.choose(key);
        }
        return returnServer;
    }
}
