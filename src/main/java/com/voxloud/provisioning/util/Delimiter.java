package com.voxloud.provisioning.util;

public enum Delimiter {
    LIST_DELIMETER_PROP_FILE(","),
    PROVISIONING_PREFIX("provisioning"),
    KEY_VAL_SEPARATOR("="),
    PROP_FILE_LINE_SEPARATOR("\n");

    private final String value;

    Delimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

