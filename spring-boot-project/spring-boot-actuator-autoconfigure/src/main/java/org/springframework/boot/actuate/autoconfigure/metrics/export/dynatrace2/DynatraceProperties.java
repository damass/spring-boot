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

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring Dynatrace
 * metrics export.
 *
 * @author Andy Wilkinson
 * @author David Mass
 * @since 2.1.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.dynatrace2")
public class DynatraceProperties extends StepRegistryProperties {

	/**
	 * Dynatrace authentication token.
	 */
	private String apiToken;

	/**
	 * Tag used as dimension for grouping metrics in Dynatrace.
	 */
	private String deviceName;

	/**
	 * Tag used as dimension for grouping metrics in Dynatrace.
	 */
	private String groupName;

	/**
	 * Entity (HOST, PG, PGI, Custom Device, Custom Device Group) to associate the metrics
	 * with.
	 */
	private String entityId;

	/**
	 * URI to ship metrics to. Should be used for SaaS, self managed instances, to
	 * en-route through an internal proxy or with v2 use the OneAgent localhost uri :
	 * https://www.dynatrace.com/support/help/how-to-use-dynatrace/metrics/metric-ingestion/ingestion-methods/local-api/
	 */
	private String uri;

	private Integer batchSize;

	public String getApiToken() {
		return this.apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEntityId() {
		return this.entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getBatchSize() {
		return this.batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
