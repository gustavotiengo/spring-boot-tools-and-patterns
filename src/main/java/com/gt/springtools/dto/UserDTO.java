package com.gt.springtools.dto;

import com.gt.springtools.tables.records.UserRecord;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.beans.ConstructorProperties;
import java.io.Serial;
import java.time.LocalDateTime;

public class UserDTO extends BaseEntityDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @NotBlank
    private final String phone;
    @Email
    @NotNull
    @NotBlank
    private final String email;
    @Size(min = 5)
    private final String address;
    @NotNull
    @NotBlank
    private final String region;
    @NotNull
    @NotBlank
    private final String city;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 2)
    private final String state;

    @ConstructorProperties({"externalId", "name", "phone", "email", "address", "region", "city", "state", "createdAt", "lastUpdate"})
    public UserDTO(String externalId,
                   String name,
                   String phone,
                   String email,
                   String address,
                   String region,
                   String city,
                   String state,
                   LocalDateTime createdAt,
                   LocalDateTime lastUpdate) {
        super(externalId, createdAt, lastUpdate);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.region = region;
        this.city = city;
        this.state = state;
    }

    public UserDTO(UserRecord userRecord) {
        super(userRecord.getExternalId() != null ? userRecord.getExternalId().toString() : null,
                userRecord.getCreatedAt(),
                userRecord.getLastUpdate());
        this.name = userRecord.getName();
        this.phone = userRecord.getPhone();
        this.email = userRecord.getEmail();
        this.address = userRecord.getAddress();
        this.region = userRecord.getRegion();
        this.city = userRecord.getCity();
        this.state = userRecord.getState();
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public String getRegion() {
        return this.region;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserDTO other = (UserDTO) obj;
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((super.getExternalId() == null) ? 0 : super.getExternalId().hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserDTO (");
        sb.append(super.getExternalId());
        sb.append(", ").append(name);
        sb.append(", ").append(phone);
        sb.append(", ").append(email);
        sb.append(", ").append(address);
        sb.append(", ").append(region);
        sb.append(", ").append(city);
        sb.append(", ").append(state);
        sb.append(", ").append(super.getCreatedAt());
        sb.append(", ").append(super.getLastUpdate());
        sb.append(")");
        return sb.toString();
    }
}
