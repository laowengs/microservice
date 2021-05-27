/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.laowengs.eureka.client;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.boot.logging.LoggerGroups;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link Endpoint @Endpoint} to expose a collection of {@link LoggerConfiguration}s.
 *
 * @author Ben Hale
 * @author Phillip Webb
 * @author HaiTao Zhang
 * @since 2.0.0
 */
@Endpoint(id = "loggers2")
public class LoggersEndpoint2 {

	LogbackLoggingSystem logbackLoggingSystem = new LogbackLoggingSystem(LoggersEndpoint2.class.getClassLoader());

	@ReadOperation
	public Map<String, Object> loggers2() {
		Collection<LoggerConfiguration> configurations = logbackLoggingSystem.getLoggerConfigurations();
		if (configurations == null) {
			return Collections.emptyMap();
		}
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("levels", logbackLoggingSystem.getSupportedLogLevels());
		result.put("loggers", getLoggers(configurations));
		result.put("groups", "getGroups()");
		return result;
	}
	private Map<String, LoggersEndpoint.LoggerLevels> getLoggers(Collection<LoggerConfiguration> configurations) {
		Map<String, LoggersEndpoint.LoggerLevels> loggers = new LinkedHashMap<>(configurations.size());
		for (LoggerConfiguration configuration : configurations) {
			loggers.put(configuration.getName(), new LoggersEndpoint.SingleLoggerLevels(configuration));
		}
		return loggers;
	}





}
