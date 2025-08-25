package com.freightfox.loadbalancer.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {
    private String vehicleId;
    private Integer totalLoad;
    private String totalDistance;
    private List<Order> assignedOrders;
}
