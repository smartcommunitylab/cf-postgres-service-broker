/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.servicebroker.postgresql.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {


    @Bean
    public Catalog catalog() throws IOException {
        ServiceDefinition serviceDefinition = new ServiceDefinition(
        		"pg", 
        		"PostgreSQL",
                "PostgreSQL on shared instance.", 
                true, 
                false,
                getPlans(), getTags(), getServiceDefinitionMetadata(),
                Arrays.asList("syslog_drain"), null);
        return new Catalog(Arrays.asList(serviceDefinition));
    }

    private static List<String> getTags() {
        return Arrays.asList("PostgreSQL", "relational");
    }

    private static Map<String, Object> getServiceDefinitionMetadata() {
        Map<String, Object> sdMetadata = new HashMap<String, Object>();
        sdMetadata.put("displayName", "PostgreSQL");
        sdMetadata.put("imageUrl", "https://wiki.postgresql.org/images/3/30/PostgreSQL_logo.3colors.120x120.png");
        sdMetadata.put("longDescription", "PostgreSQL Service");
        sdMetadata.put("providerDisplayName", "PostgreSQL");
        sdMetadata.put("documentationUrl", "http://mendix.com/postgresql");
        sdMetadata.put("supportUrl", "https://support.mendix.com");
        return sdMetadata;
    }

    private static List<Plan> getPlans() {
        Plan basic = new Plan("postgresql-basic-plan", "Basic PostgreSQL Plan",
                "A PG plan providing a single database on a shared instance with limited storage.", getBasicPlanMetadata());
        return Arrays.asList(basic);
    }

    private static Map<String, Object> getBasicPlanMetadata() {
        Map<String, Object> planMetadata = new HashMap<String, Object>();
        planMetadata.put("costs", getCosts());
        planMetadata.put("bullets", getBasicPlanBullets());
        return planMetadata;
    }

	private static List<Map<String,Object>> getCosts() {
		Map<String,Object> costsMap = new HashMap<>();
		
		Map<String,Object> amount = new HashMap<>();
		amount.put("usd", 0.0);
	
		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");
		
		return Collections.singletonList(costsMap);
	}
    private static List<String> getBasicPlanBullets() {
        return Arrays.asList("Single PG database", "Limited storage", "Shared instance");
    }
}