package com.lits.springboot.model;

public enum Permissions {
    CREATE_USER("CREATE_USER"),
    DELETE_USER("DELETE_USER"),
    READ_USER("READ_USER");

    public final String code;

    Permissions(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
