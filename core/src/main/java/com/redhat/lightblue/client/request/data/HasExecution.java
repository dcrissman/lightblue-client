package com.redhat.lightblue.client.request.data;

import com.redhat.lightblue.client.Execution;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;

public interface HasExecution {

    Execution getExecution();

    AbstractLightblueDataRequest execution(Execution execution);

}
