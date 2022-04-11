package com.tms.common.domain.enumTypes.auth;


public enum Authority  {
    READ("READ"),
    UPDATE("UPDATE"),
    CREATE("CREATE"),
    DELETE("DELETE"),
    APPROVE("APPROVE");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }
}
