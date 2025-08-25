package com.freightfox.loadbalancer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class VehicleRequest {

    @NotEmpty
    @Valid
    @JsonProperty("vehicles")
    private List<Vehicle> vehicle;
}
