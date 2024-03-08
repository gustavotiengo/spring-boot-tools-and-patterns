package com.gt.springtools.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gt.springtools.Constants;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseEntityDTO implements Serializable {

    @Pattern(regexp = Constants.UUID_V4)
    @Null
    private final String externalId;

    @Null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime createdAt;
    @Null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime lastUpdate;

    public BaseEntityDTO(String externalId, LocalDateTime createdAt, LocalDateTime lastUpdate) {
        this.externalId = externalId;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
    }

    public String getExternalId() {
        return externalId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

}
