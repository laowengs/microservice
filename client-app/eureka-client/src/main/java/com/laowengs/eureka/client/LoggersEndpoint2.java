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

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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






}
