package com.freightfox.loadbalancer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatchDTO {
    private List<PlanResponse> assignedOrders;
    private List<Order> unassignedOrders;
}
