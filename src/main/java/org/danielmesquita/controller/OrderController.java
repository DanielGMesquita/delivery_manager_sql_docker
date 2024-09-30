package org.danielmesquita.controller;

import java.util.List;
import org.danielmesquita.dto.OrderDTO;
import org.danielmesquita.entities.Order;
import org.danielmesquita.service.OrderService;
import org.danielmesquita.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired private OrderService orderService;
  @Autowired private ProductService productService;

  @PostMapping
  public ResponseEntity<Order> createNewOrder(@RequestBody OrderDTO orderDTO) {
    if (orderDTO.getProductIdList() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product list cannot be empty");
    }

    Order newOrder = orderService.insertOrder(orderDTO);

    return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.findAllOrders();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public Order updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDetails) {
    return orderService.updateOrder(orderDetails, id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrderById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
