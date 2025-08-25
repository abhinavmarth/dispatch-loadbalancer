package com.freightfox.loadbalancer.controller;

import com.freightfox.loadbalancer.model.DispatchDTO;
import com.freightfox.loadbalancer.model.*;
import com.freightfox.loadbalancer.service.LoadBalancerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/dispatch")
public class LoadbalancerController {

    @Autowired
    private LoadBalancerService loadBalancerService;

    @PostMapping("/orders")
    public Map<String, String> orderList(@Valid @RequestBody OrdersRequest orderReq) {
        loadBalancerService.listOfOrders(orderReq);
        Map<String,String> mp = new HashMap<>();
        mp.put("message","Delivery orders accepted.");
        mp.put("status","success");
        return mp;
    }

    @PostMapping("/vehicles")
    public Map<String, String> vehicleList(@Valid @RequestBody VehicleRequest vehicleReq) {
        loadBalancerService.listOfVehicles(vehicleReq);
        Map<String,String> mp = new HashMap<>();
        mp.put("message","Vehicle details accepted.");
        mp.put("status","success");
        return mp;
    }

    @GetMapping("/plan")
    public Map<String, List<PlanResponse>> plan() {
        List<PlanResponse> dto = loadBalancerService.plan();
        return Map.of("dispatchPlan",dto);
    }

}
