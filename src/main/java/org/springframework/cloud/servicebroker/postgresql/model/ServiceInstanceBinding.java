package org.springframework.cloud.servicebroker.postgresql.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * A binding to a service instance
 *
 * @author sgreenberg@pivotal.io
 */
@Entity
public class ServiceInstanceBinding {

	@Id
	private String id;
	private String serviceInstanceId;
	private String syslogDrainUrl;
	private String appGuid;
	private String uri;
	
	@Transient
	private Map<String,Object> credentials = new HashMap<>();

	public ServiceInstanceBinding() {
	}

	public ServiceInstanceBinding(String id,
								  String serviceInstanceId,
								  Map<String,Object> credentials,
								  String syslogDrainUrl, String appGuid) {
		this.id = id;
		this.serviceInstanceId = serviceInstanceId;
		setCredentials(credentials);
		this.syslogDrainUrl = syslogDrainUrl;
		this.appGuid = appGuid;
	}

	public String getId() {
		return id;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public Map<String, Object> getCredentials() {
		if (credentials == null || credentials.isEmpty())  {
			credentials = new HashMap<>();
			credentials.put("uri", getUri());
		}
		return credentials;
	}

	private void setCredentials(Map<String, Object> credentials) {
		if (credentials == null) {
			this.credentials = new HashMap<>();
		} else {
			this.credentials = credentials;
		}
	}

	public String getSyslogDrainUrl() {
		return syslogDrainUrl;
	}

	public String getAppGuid() {
		return appGuid;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	
}