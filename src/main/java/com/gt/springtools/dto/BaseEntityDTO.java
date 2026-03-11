package com.gt.springtools.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gt.springtools.Constants;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface BaseEntityDTO extends Serializable {

    @Pattern(regexp = Constants.UUID_V4)
    @Null
    String getExternalId();

    @Null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDateTime getCreatedAt();

    @Null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDateTime getLastUpdate();

}
