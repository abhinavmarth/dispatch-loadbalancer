package com.freightfox.loadbalancer.service;

import com.freightfox.loadbalancer.model.*;
import com.freightfox.loadbalancer.repository.OrderRepository;
import com.freightfox.loadbalancer.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoadBalancerService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    public List<Order> listOfOrders(OrdersRequest orderReq) {
        return orderRepo.saveAll(orderReq.getOrders());
    }

    public List<Vehicle> listOfVehicles(VehicleRequest vehicleReq) {
        return vehicleRepo.saveAll(vehicleReq.getVehicle());
    }

    public List<PlanResponse> plan() {
        List<Order> orders = new ArrayList<>(orderRepo.findAll());
        List<Vehicle> vehicles = new ArrayList<>(vehicleRepo.findAll());

        orders.sort(Comparator.comparingInt(o -> o.getPriority().ordinal()));

        Map<String, List<Order>> vehicleAssignedOrders = new HashMap<>();
        Map<String, Integer> vehicleLoad = new HashMap<>();
        Map<String, Double> vehicleDistance = new HashMap<>();
        Map<String, double[]> vehicleCurrentLocation = new HashMap<>();
        List<Order> unassigned  = new ArrayList<>();
        for (Vehicle v : vehicles) {
            vehicleAssignedOrders.put(v.getVehicleId(), new ArrayList<>());
            vehicleLoad.put(v.getVehicleId(), 0);
            vehicleDistance.put(v.getVehicleId(), 0.0);
            vehicleCurrentLocation.put(v.getVehicleId(), new double[]{v.getCurrLatitude(), v.getCurrLongitude()});
        }

        for (Order ord : orders) {
            double minDistance = Double.MAX_VALUE;
            Vehicle bestVehicle = null;

            for (Vehicle v : vehicles) {
                if (v.getCapacity() >= ord.getWeight()) {
                    double[] loc = vehicleCurrentLocation.get(v.getVehicleId());
                    double dist = haversineDistance(loc[0], loc[1], ord.getLatitude(), ord.getLongitude());
                    if (dist < minDistance) {
                        minDistance = dist;
                        bestVehicle = v;
                    }
                }
            }

            if (bestVehicle == null) {
                unassigned.add(ord);
                continue;
            }

            String vid = bestVehicle.getVehicleId();
            bestVehicle.setCapacity(bestVehicle.getCapacity() - ord.getWeight());
            vehicleAssignedOrders.get(vid).add(ord);
            vehicleLoad.put(vid, vehicleLoad.get(vid) + ord.getWeight());
            vehicleDistance.put(vid, vehicleDistance.get(vid) + minDistance);
            vehicleCurrentLocation.put(vid, new double[]{ord.getLatitude(), ord.getLongitude()});
        }
        List<PlanResponse> planResponses = new ArrayList<>();
        for (Vehicle v : vehicles) {
            String vid = v.getVehicleId();
            List<Order> assigned = vehicleAssignedOrders.get(vid);
            if (!assigned.isEmpty()) {
                double km = vehicleDistance.get(vid);
                String pretty = String.format(Locale.US, "%.1f km", km);
                planResponses.add(new PlanResponse(
                        vid,
                        vehicleLoad.get(vid),
                        pretty,
                        assigned
                ));
            }
        }
        DispatchDTO dispatchDTO = new DispatchDTO(planResponses,unassigned);
        return planResponses;
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(Math.toRadians(lat1)) *
                                Math.cos(Math.toRadians(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
