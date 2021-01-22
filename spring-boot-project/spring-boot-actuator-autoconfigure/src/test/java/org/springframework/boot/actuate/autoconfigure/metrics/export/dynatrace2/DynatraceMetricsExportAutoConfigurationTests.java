/*
 * Copyright 2012-2021 the original author or authors.
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

package org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2;

import java.util.function.Function;

import io.micrometer.core.instrument.Clock;
import io.micrometer.dynatrace2.DynatraceConfig;
import io.micrometer.dynatrace2.DynatraceMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for
 * {@link org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceMetricsExportAutoConfiguration}.
 *
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 * @author David Mass
 */
class DynatraceMetricsExportAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(DynatraceMetricsExportAutoConfiguration.class));

	@Test
	void backsOffWithoutAClock() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(DynatraceMeterRegistry.class));
	}

	@Test
	void failsWithoutAUri() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.run((context) -> assertThat(context).hasFailed());
	}

	@Test
	void autoConfiguresConfigAndMeterRegistry() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).with(mandatoryProperties())
				.run((context) -> assertThat(context).hasSingleBean(DynatraceMeterRegistry.class)
						.hasSingleBean(DynatraceConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithDefaultsEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.defaults.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(DynatraceMeterRegistry.class)
						.doesNotHaveBean(DynatraceConfig.class));
	}

	@Test
	void autoConfigurationCanBeDisabledWithSpecificEnabledProperty() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class)
				.withPropertyValues("management.metrics.export.dynatrace2.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean(DynatraceMeterRegistry.class)
						.doesNotHaveBean(DynatraceConfig.class));
	}

	@Test
	void allowsCustomConfigToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomConfigConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(DynatraceMeterRegistry.class)
						.hasSingleBean(DynatraceConfig.class).hasBean("customConfig"));
	}

	@Test
	void allowsCustomConfigLocalhostToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomConfigConfiguration2.class)
				.run((context) -> assertThat(context).hasSingleBean(DynatraceMeterRegistry.class)
						.hasSingleBean(DynatraceConfig.class).hasBean("customConfig2"));
	}

	@Test
	void allowsCustomRegistryToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomRegistryConfiguration.class).with(mandatoryProperties())
				.run((context) -> assertThat(context).hasSingleBean(DynatraceMeterRegistry.class)
						.hasBean("customRegistry").hasSingleBean(DynatraceConfig.class));
	}

	@Test
	void stopsMeterRegistryWhenContextIsClosed() {
		this.contextRunner.withUserConfiguration(BaseConfiguration.class).with(mandatoryProperties()).run((context) -> {
			DynatraceMeterRegistry registry = context.getBean(DynatraceMeterRegistry.class);
			assertThat(registry.isClosed()).isFalse();
			context.close();
			assertThat(registry.isClosed()).isTrue();
		});
	}

	private Function<ApplicationContextRunner, ApplicationContextRunner> mandatoryProperties() {
		return (runner) -> runner.withPropertyValues(
				"management.metrics.export.dynatrace2.uri=https://dynatrace.example.com",
				"management.metrics.export.dynatrace2.apiToken=12345");
	}

	@Configuration(proxyBeanMethods = false)
	static class BaseConfiguration {

		@Bean
		Clock clock() {
			return Clock.SYSTEM;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomConfigConfiguration {

		@Bean
		DynatraceConfig customConfig() {
			return (key) -> {
				switch (key) {
				case "dynatrace2.uri":
					return "https://dynatrace.example.com";
				case "dynatrace2.apiToken":
					return "abcde";
				case "dynatrace2.deviceName":
					return "test";
				default:
					return null;
				}
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomConfigConfiguration2 {

		@Bean
		DynatraceConfig customConfig2() {
			return (key) -> {
				switch (key) {
				case "dynatrace2.uri":
					return "https://localhost:14999/metrics/ingest";
				case "dynatrace2.deviceName":
					return "test";
				default:
					return null;
				}
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseConfiguration.class)
	static class CustomRegistryConfiguration {

		@Bean
		DynatraceMeterRegistry customRegistry(DynatraceConfig config, Clock clock) {
			return new DynatraceMeterRegistry(config, clock);
		}

	}

}
