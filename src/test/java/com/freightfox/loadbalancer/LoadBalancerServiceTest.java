package com.freightfox.loadbalancer;

import com.freightfox.loadbalancer.model.Order;
import com.freightfox.loadbalancer.model.PlanResponse;
import com.freightfox.loadbalancer.model.Priority;
import com.freightfox.loadbalancer.model.Vehicle;
import com.freightfox.loadbalancer.repository.OrderRepository;
import com.freightfox.loadbalancer.repository.VehicleRepository;
import com.freightfox.loadbalancer.service.LoadBalancerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoadBalancerServiceTest {

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private VehicleRepository vehicleRepo;

    @InjectMocks
    private LoadBalancerService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAssignOrderToNearestVehicleWithCapacity() {
        Order o1 = new Order(1L, "O1", 28.5, 77.0, 10, Priority.HIGH, "Warangal");
        Vehicle v1 = new Vehicle(1L, "V1", 20, 28.6, 77.3, "Delhi");
        when(orderRepo.findAll()).thenReturn(List.of(o1));
        when(vehicleRepo.findAll()).thenReturn(List.of(v1));

        List<PlanResponse> result = service.plan();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAssignedOrders().size());
        assertEquals("O1", result.get(0).getAssignedOrders().get(0).getOrderId());
    }

    @Test
    void shouldNotAssignWhenCapacityIsLess() {
        Order o1 = new Order(1L, "O1", 28.5, 77.0, 50, Priority.HIGH, "Warangal");
        Vehicle v1 = new Vehicle(1L, "V1", 20, 28.6, 77.3, "Delhi");
        when(orderRepo.findAll()).thenReturn(List.of(o1));
        when(vehicleRepo.findAll()).thenReturn(List.of(v1));

        List<PlanResponse> result = service.plan();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldAssignMultipleOrdersToSingleVehicle() {
        Order o1 = new Order(1L, "O1", 28.5, 77.0, 10, Priority.HIGH, "Warangal");
        Order o2 = new Order(2L, "O2", 58.5, 97.0, 13, Priority.LOW, "Hanamkonda");
        Vehicle v1 = new Vehicle(1L, "V1", 50, 28.6, 77.3, "Delhi");
        when(orderRepo.findAll()).thenReturn(Arrays.asList(o1, o2));
        when(vehicleRepo.findAll()).thenReturn(List.of(v1));

        List<PlanResponse> result = service.plan();

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getAssignedOrders().size());
    }

    @Test
    void shouldDistributeOrdersAcrossVehicles() {
        Order o1 = new Order(1L, "O1", 28.5, 77.2, 10, Priority.HIGH, "Hyderabad");
        Order o2 = new Order(2L, "O2", 28.6, 77.4, 15, Priority.LOW, "Secunderabad");
        Vehicle v1 = new Vehicle(1L, "V1", 20, 28.6, 77.3, "Delhi");
        Vehicle v2 = new Vehicle(2L, "V2", 20, 28.4, 77.1, "Noida");
        when(orderRepo.findAll()).thenReturn(Arrays.asList(o1, o2));
        when(vehicleRepo.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<PlanResponse> result = service.plan();

        assertEquals(2, result.size());
        int totalOrders = result.stream().mapToInt(r -> r.getAssignedOrders().size()).sum();
        assertEquals(2, totalOrders);
    }

    @Test
    void shouldReturnEmptyWhenNoOrders() {
        Vehicle v1 = new Vehicle(1L, "V1", 20, 28.6, 77.3, "Delhi");
        when(orderRepo.findAll()).thenReturn(Collections.emptyList());
        when(vehicleRepo.findAll()).thenReturn(List.of(v1));

        List<PlanResponse> result = service.plan();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenNoVehicles() {
        Order o1 = new Order(1L, "O1", 28.5, 77.2, 10, Priority.HIGH, "Warangal");
        when(orderRepo.findAll()).thenReturn(List.of(o1));
        when(vehicleRepo.findAll()).thenReturn(Collections.emptyList());

        List<PlanResponse> result = service.plan();

        assertTrue(result.isEmpty());
    }
}
