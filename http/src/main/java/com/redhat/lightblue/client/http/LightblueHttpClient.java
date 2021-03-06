package com.redhat.lightblue.client.http;

import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.http.auth.HttpClientCertAuth;
import com.redhat.lightblue.client.http.auth.HttpClientNoAuth;
import com.redhat.lightblue.client.http.request.LightblueHttpDataRequest;
import com.redhat.lightblue.client.http.request.LightblueHttpMetadataRequest;
import com.redhat.lightblue.client.request.LightblueRequest;
import com.redhat.lightblue.client.response.LightblueResponse;

public class LightblueHttpClient implements LightblueClient {

	private String configFilePath = "lightblue-client.properties";
	private String dataServiceURI;
	private String metadataServiceURI;
	private boolean useCertAuth = false;

	private static final Logger LOGGER = LoggerFactory.getLogger(LightblueHttpClient.class);

	public LightblueHttpClient() {
		try {
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream(configFilePath));
			metadataServiceURI = properties.getProperty("metadataServiceURI");
			dataServiceURI = properties.getProperty("dataServiceURI");
			if (metadataServiceURI == null && dataServiceURI == null) {
				throw new RuntimeException("Either metadataServiceURI or dataServiceURI must be defined in appconfig.properties");
			}
			useCertAuth = Boolean.parseBoolean(properties.getProperty("useCertAuth"));
		} catch (IOException io) {
			LOGGER.error("lightblue-client.properties could not be found/read", io);
			throw new RuntimeException(io);
		}
	}

	/* (non-Javadoc)
	 * @see com.redhat.lightblue.client.LightblueClient#setConfigFilePath(java.lang.String)
	 */
	@Override
  public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	/* (non-Javadoc)
	 * @see com.redhat.lightblue.client.LightblueClient#getConfigFilePath()
	 */
	@Override
  public String getConfigFilePath() {
		return configFilePath;
	}

	/* (non-Javadoc)
	 * @see com.redhat.lightblue.client.LightblueClient#metadata(com.redhat.lightblue.client.request.LightblueRequest)
	 */
	@Override
  public LightblueResponse metadata(LightblueRequest lightblueRequest) {
		LOGGER.debug("Calling metadata service with lightblueRequest: " + lightblueRequest.toString());
		return callService(new LightblueHttpMetadataRequest(lightblueRequest).getRestRequest(metadataServiceURI));
	}

	/* (non-Javadoc)
	 * @see com.redhat.lightblue.client.LightblueClient#data(com.redhat.lightblue.client.request.LightblueRequest)
	 */
	@Override
  public LightblueResponse data(LightblueRequest lightblueRequest) {
		LOGGER.debug("Calling data service with lightblueRequest: " + lightblueRequest.toString());
		return callService(new LightblueHttpDataRequest(lightblueRequest).getRestRequest(dataServiceURI));
	}

	private LightblueResponse callService(HttpRequestBase httpOperation) {
		String jsonOut;
		try {
			try (CloseableHttpClient httpClient = getLightblueHttpClient()) {
				httpOperation.setHeader("Content-Type", "application/json");

				try (CloseableHttpResponse httpResponse = httpClient.execute(httpOperation)) {
					HttpEntity entity = httpResponse.getEntity();
					jsonOut = EntityUtils.toString(entity);
					LOGGER.debug("Response received from service" + jsonOut);
					return new LightblueResponse(jsonOut);
				}
			}
		} catch (IOException e) {
			LOGGER.error("There was a problem calling the lightblue service", e);
			return new LightblueResponse("{\"error\":\"There was a problem calling the lightblue service\"}");
		}
	}

	
	private CloseableHttpClient getLightblueHttpClient() {
		if (useCertAuth) {
			LOGGER.debug("Using certificate authentication");
			return new HttpClientCertAuth().getClient();
		} else {
			LOGGER.debug("Using no authentication");
			return new HttpClientNoAuth().getClient();
		}
	}

}
