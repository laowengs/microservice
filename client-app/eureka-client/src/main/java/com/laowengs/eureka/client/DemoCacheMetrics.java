package com.laowengs.eureka.client;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;


@Component
public class DemoCacheMetrics implements MeterBinder {

  @Override
  public void bindTo(MeterRegistry registry) {
    Gauge.builder("redis.queue.failed", () -> getFailedCount())
        .baseUnit("次数")
        .description("redis queue failed size count")
        .register(registry);
    Gauge.builder("redis.queue.tohandle", () -> getToHandleCount())
        .baseUnit("次数").tags("1","2","3","4")
        .description("redis queue to handle size count")
        .register(registry);
  }

  private Number getFailedCount() {
    return System.currentTimeMillis()/1000;
  }

  private Number getToHandleCount() {
    return System.currentTimeMillis();
  }
}