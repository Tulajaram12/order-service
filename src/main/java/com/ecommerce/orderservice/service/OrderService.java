package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderRequest request) {

        Order order = new Order(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity(),
                request.getTotal(),
                "CREATED"
        );

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {

        return orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(
            Long id,
            String status) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {

        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        orderRepository.deleteById(id);
    }
}
