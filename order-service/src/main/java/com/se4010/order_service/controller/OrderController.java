package com.se4010.order_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private List<Map<String, Object>> orders = new ArrayList<>();
    private int idCounter = 1;

    // GET /orders
    @GetMapping
    public List<Map<String, Object>> getOrders() {
        return orders;
    }

    // POST /orders
    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Map<String, Object> order) {
        order.put("id", idCounter++);
        order.put("status", "PENDING");
        orders.add(order);
        return ResponseEntity.status(201).body(order);
    }

    // GET /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
        return orders.stream()
                .filter(o -> o.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}