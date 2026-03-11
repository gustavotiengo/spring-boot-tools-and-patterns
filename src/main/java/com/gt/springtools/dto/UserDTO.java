package com.gt.springtools.dto;

import com.gt.springtools.tables.records.UserRecord;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gt.springtools.Constants;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record UserDTO(
        @Pattern(regexp = Constants.UUID_V4) @Null String externalId,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String phone,
        @Email @NotNull @NotBlank String email,
        @Size(min = 5) String address,
        @NotNull @NotBlank String region,
        @NotNull @NotBlank String city,
        @NotNull @NotBlank @Size(min = 2, max = 2) String state,
        @Null @JsonInclude(JsonInclude.Include.NON_NULL) LocalDateTime createdAt,
        @Null @JsonInclude(JsonInclude.Include.NON_NULL) LocalDateTime lastUpdate
) implements BaseEntityDTO {

    public UserDTO(UserRecord userRecord) {
        this(
                userRecord.getExternalId() != null ? userRecord.getExternalId().toString() : null,
                userRecord.getName(),
                userRecord.getPhone(),
                userRecord.getEmail(),
                userRecord.getAddress(),
                userRecord.getRegion(),
                userRecord.getCity(),
                userRecord.getState(),
                userRecord.getCreatedAt(),
                userRecord.getLastUpdate()
        );
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
