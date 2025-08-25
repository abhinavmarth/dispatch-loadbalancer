package com.freightfox.loadbalancer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="vehicles", uniqueConstraints = @UniqueConstraint(columnNames = "vehicleId"))
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String vehicleId;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
    @JsonProperty("currentLatitude")
    private Double currLatitude;

    @NotNull
    @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
    @JsonProperty("currentLongitude")
    private Double currLongitude;

    @NotBlank
    @JsonProperty("currentAddress")
    private String currAddress;
}
