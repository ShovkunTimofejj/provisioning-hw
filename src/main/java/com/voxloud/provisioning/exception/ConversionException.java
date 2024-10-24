package com.voxloud.provisioning.exception;

import lombok.Getter;

@Getter
public class ConversionException extends Exception{
    private String code;
    private String feature;
    private String reason;

    public ConversionException(String feature, String code, String reason) {
        super(reason);
        this.reason=reason;
        this.feature = feature;
        this.code = code;
    }
}
