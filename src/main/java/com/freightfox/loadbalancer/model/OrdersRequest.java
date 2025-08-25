package com.freightfox.loadbalancer.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequest {

    @NotEmpty
    @Valid
    private List<Order> orders;
}
