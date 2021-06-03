package com.laowengs.security;

public enum RoleEnum {
    ROLE_SUPER_ADMIN(999),
    ROLE_ADMIN(99),
    ROLE_LOGIN(1),
    ;

    private int priority;

    RoleEnum(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
