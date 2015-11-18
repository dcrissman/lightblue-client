package com.redhat.lightblue.client.request;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.redhat.lightblue.client.util.JSON;

public abstract class AbstractLightblueRequest implements LightblueRequest {

    protected static final String PATH_SEPARATOR = "/";
    protected static final String QUERY_SEPARATOR = "&";
	protected static final String QUERY_BEGINNER = "?";
	protected static final String QUERY_PARAM_NAME_VALUE_SEPERATOR = "=";

    private String entityName;
    private String entityVersion;

    public AbstractLightblueRequest() {}

    /**
     * Construct request with entity name and default version
     */
    public AbstractLightblueRequest(String entityName) {
        this(entityName, null);
    }

    /**
     * Construct request with entity name and given version
     */
    public AbstractLightblueRequest(String entityName, String version) {
        this.entityName=entityName;
        entityVersion=version;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityVersion() {
        return entityVersion;
    }

    @Deprecated
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Deprecated
    public void setEntityVersion(String entityVersion) {
        this.entityVersion = entityVersion;
    }

    public static void appendToURI(StringBuilder restOfURI, String pathParam) {
        if (!StringUtils.endsWith(restOfURI.toString(), PATH_SEPARATOR)) {
            restOfURI.append(PATH_SEPARATOR);
        }
        restOfURI.append(pathParam);
    }

	protected void appendToURI(StringBuilder restOfURI, String queryParamName, String queryParamvalue) {
		if (!StringUtils.endsWith(restOfURI.toString(), PATH_SEPARATOR)) {
			if (!StringUtils.contains(restOfURI.toString(), QUERY_PARAM_NAME_VALUE_SEPERATOR)) {
				restOfURI.append(QUERY_BEGINNER);
			} else {
				restOfURI.append(QUERY_SEPARATOR);
			}
			restOfURI.append(queryParamName);
			restOfURI.append(QUERY_PARAM_NAME_VALUE_SEPERATOR);
			restOfURI.append(queryParamvalue);
		}

	}


    @Override
    public String toString() {
        return getHttpMethod().toString()+" "+getRestURI("/")+", body: "+getBody();
    }
}
