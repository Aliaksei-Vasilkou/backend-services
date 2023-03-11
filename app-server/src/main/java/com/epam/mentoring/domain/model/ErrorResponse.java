package com.epam.mentoring.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private String code;

    private String message;

    private String source;
}
