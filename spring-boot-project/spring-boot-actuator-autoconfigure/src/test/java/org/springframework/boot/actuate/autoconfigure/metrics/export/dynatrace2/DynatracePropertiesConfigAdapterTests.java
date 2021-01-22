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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for
 * {@link org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatracePropertiesConfigAdapter}.
 *
 * @author Andy Wilkinson
 * @author David Mass
 */
class DynatracePropertiesConfigAdapterTests {

	@Test
	void whenPropertiesUriIsSetAdapterUriReturnsIt() {
		org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties properties = new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties();
		properties.setUri("https://dynatrace.example.com");
		assertThat(
				new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatracePropertiesConfigAdapter(
						properties).uri()).isEqualTo("https://dynatrace.example.com");
	}

	@Test
	void whenPropertiesApiTokenIsSetAdapterApiTokenReturnsIt() {
		org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties properties = new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties();
		properties.setApiToken("123ABC");
		assertThat(
				new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatracePropertiesConfigAdapter(
						properties).apiToken()).isEqualTo("123ABC");
	}

	@Test
	void whenPropertiesDeviceIdIsSetAdapterDeviceIdReturnsIt() {
		org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties properties = new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties();
		properties.setDeviceName("dev-1");
		assertThat(
				new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatracePropertiesConfigAdapter(
						properties).deviceName()).isEqualTo("dev-1");
	}

	@Test
	void whenPropertiesTechnologyTypeIsSetAdapterTechnologyTypeReturnsIt() {
		org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties properties = new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties();
		properties.setEntityId("tech-1");
		assertThat(
				new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatracePropertiesConfigAdapter(
						properties).entityId()).isEqualTo("tech-1");
	}

	@Test
	void whenPropertiesGroupIsSetAdapterGroupReturnsIt() {
		org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatraceProperties properties = new DynatraceProperties();
		properties.setGroupName("group-1");
		assertThat(
				new org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace2.DynatracePropertiesConfigAdapter(
						properties).groupName()).isEqualTo("group-1");
	}

}
