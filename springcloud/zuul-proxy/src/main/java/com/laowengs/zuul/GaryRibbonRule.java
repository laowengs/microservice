package com.laowengs.zuul;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GaryRibbonRule extends ZoneAvoidanceRule {
    private static final Logger logger = LoggerFactory.getLogger(GaryRibbonRule.class);
    private final AtomicInteger nextIndex = new AtomicInteger();


    @Override
    public Server choose(Object key) {
        String version = GrayHolder.getVersion();
        logger.debug("get request version {}", version);
        List<Server> allServerList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        List<Server> garyServerList = new ArrayList<>();
        for (Server server : allServerList) {
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
            String metaVersion = metadata.get("version");
            if (metaVersion != null) {
                if (metaVersion.equalsIgnoreCase(version)) {
                    garyServerList.add(server);
                }
            }
        }
        if (garyServerList.isEmpty()) {
            logger.info("未获取到符合版本的实例");
            return null;
        }
        return garyServerList.get(incrementAndGetModulo(garyServerList.size()));
    }

    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextIndex.get();
            int next = (current + 1) % modulo;
            if (nextIndex.compareAndSet(current, next) && current < modulo)
                return current;
        }
    }



}
