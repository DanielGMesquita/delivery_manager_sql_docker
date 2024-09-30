package org.danielmesquita.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.danielmesquita.dto.OrderDTO;
import org.danielmesquita.entities.Order;
import org.danielmesquita.entities.Product;
import org.danielmesquita.exceptions.ResourceNotFoundException;
import org.danielmesquita.repository.OrderRepository;
import org.danielmesquita.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
  @Autowired private ProductRepository productRepository;
  @Autowired private OrderRepository orderRepository;

  public List<Order> findAllOrders() {
    return orderRepository.findAll();
  }

  public Optional<Order> findOrderById(Long id) {
    return orderRepository.findById(id);
  }

  public Order insertOrder(OrderDTO orderDTO) {
    List<Product> products = populateProductList(orderDTO);

    Order order = Order.builder().orderDate(orderDTO.getDate()).products(products).build();
    return orderRepository.save(order);
  }

  public Order updateOrder(OrderDTO orderDTO, Long id) {
    Optional<Order> order = findOrderById(id);
    if (order.isPresent()) {
      Order existingOrder = order.get();
      existingOrder.setOrderDate(orderDTO.getDate());

      List<Product> products = populateProductList(orderDTO);
      existingOrder.setProducts(products);

      return orderRepository.save(existingOrder);
    } else {
      throw new ResourceNotFoundException("Entity not found for id: " + id);
    }
  }

  public void deleteOrderById(Long id) {
    orderRepository.deleteById(id);
  }

  public List<Product> populateProductList(OrderDTO orderDTO) {
    List<Product> products = new ArrayList<>();

    if (!orderDTO.getProductIdList().isEmpty()) {
      for (Long productId : orderDTO.getProductIdList()) {
        Product product =
            productRepository
                .findById(productId)
                .orElseThrow(
                    () ->
                        new ResourceNotFoundException(
                            "Product with ID " + productId + " not found"));
        products.add(product);
      }
    }

    return products;
  }
}
