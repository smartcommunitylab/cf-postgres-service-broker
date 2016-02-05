package org.springframework.cloud.servicebroker.postgresql.repository;

import org.springframework.cloud.servicebroker.postgresql.model.ServiceInstanceBinding;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for ServiceInstanceBinding objects
 * 
 * @author sgreenberg@pivotal.io
 *
 */
public interface ServiceInstanceBindingRepository extends JpaRepository<ServiceInstanceBinding, String> {

}
