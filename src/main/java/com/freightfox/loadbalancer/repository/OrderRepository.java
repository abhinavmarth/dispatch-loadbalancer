package com.freightfox.loadbalancer.repository;

import com.freightfox.loadbalancer.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {}
