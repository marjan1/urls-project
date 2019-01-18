package com.eadvocate.util;

/**
 * Enum for types of company accounts.
 */
public enum CompanyType {

    OFFICE(0),SOLO(1);

    private int value;

    CompanyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
