package org.danielmesquita.service;

import java.util.List;
import java.util.Optional;
import org.danielmesquita.entities.Order;
import org.danielmesquita.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  @Autowired private OrderRepository orderRepository;

  public List<Order> findAllOrders() {
    return orderRepository.findAll();
  }

  public Optional<Order> findOrderById(Long id) {
    return orderRepository.findById(id);
  }

  public Order insertOrder(Order order) {
    return orderRepository.save(order);
  }

  public void deleteOrderById(Long id) {
    orderRepository.deleteById(id);
  }
}
