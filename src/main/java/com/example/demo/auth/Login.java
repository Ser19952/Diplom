package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    @JsonProperty("auth-token")

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonSetter(nulls = Nulls.FAIL)
    private String authToken ;
}
