package com.redhat.lightblue.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class Execution extends Expression {

    public static final String WRITE_CONCERN_NO_ACKNOWLEDGEMENT = "0";
    public static final String WRITE_CONCERN_WAIT_FOR_ACKNOWLEDGEMENT = "1";
    public static final String WRITE_CONCERN_WAIT_FOR_SECONDARIES_TO_ACKNOWLEDGE = ">=2";
    public static final String WRITE_CONCERN_MAJORITY = "majority";

    public enum READ_CONERN {
        PRIMARY,
        PRIMARY_PREFERRED,
        SECONDARY,
        SECONDARY_PREFERRED,
        NEAREST;

    }

    protected Execution() {
        super(false);
    }

    public Execution writeConcern(String value) {
        add("writeConcern", value);
        return this;
    }

    public Execution readConcern(READ_CONERN value) {
        add("readPreference", value.name());
        return this;
    }

    public Execution maxQueryTimeMS(long maxTimeMS) {
        add("maxQueryTimeMS", JsonNodeFactory.instance.numberNode(maxTimeMS));
        return this;
    }

}
