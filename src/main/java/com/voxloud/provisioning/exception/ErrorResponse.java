package com.voxloud.provisioning.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    String feature;
    String code;
    String message;
}
