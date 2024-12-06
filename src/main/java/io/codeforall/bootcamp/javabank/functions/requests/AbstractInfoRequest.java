package io.codeforall.bootcamp.javabank.functions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractInfoRequest {

    @JsonProperty(required = false)
    String dummy;
}
