package org.springframework.cloud.servicebroker.postgresql.repository;

import org.springframework.cloud.servicebroker.postgresql.model.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for ServiceInstance objects
 * 
 * @author sgreenberg@pivotal.io
 *
 */
public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, String> {

}