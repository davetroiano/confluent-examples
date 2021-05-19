package com.dave.test;

import com.fasterxml.jackson.annotation.JsonSetter;


/**
 * Sample POJO class.
 *
 * Note that the @JsonSetter Jackson annotation is required for spring-kafka deserialization to work.
 * It'd also work if all fields are public.
 */
public class Foo
{
    private String v1;
    private String v2;

    public String getV1() {
        return v1;
    }

    @JsonSetter("VALUE1")
    public void setV1(String V1) {
        this.v1 = V1;
    }
    public String getV2() {
        return v2;
    }
    @JsonSetter("VALUE2")
    public void setV2(String v2) {
        this.v2 = v2;
    }

    @Override
    public String toString() {
        return "com.dave.test.Foo [VALUE1=" + v1 + ", VALUE2=" + v2 + "]";
    }
}
