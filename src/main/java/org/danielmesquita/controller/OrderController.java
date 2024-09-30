package org.danielmesquita.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.danielmesquita.dto.OrderDTO;
import org.danielmesquita.entities.Order;
import org.danielmesquita.entities.Product;
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

    List<Product> products = populateProductList(orderDTO);

    Order order = Order.builder().orderDate(orderDTO.getDate()).products(products).build();

    orderService.insertOrder(order);

    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.findAllOrders();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(
      @PathVariable Long id, @RequestBody OrderDTO orderDetails) {
    Optional<Order> order = orderService.findOrderById(id);
    if (order.isPresent()) {
      Order existingOrder = order.get();
      existingOrder.setOrderDate(orderDetails.getDate());

      List<Product> products = populateProductList(orderDetails);
      existingOrder.setProducts(products);

      orderService.insertOrder(existingOrder);
      return new ResponseEntity<>(existingOrder, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrderById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public List<Product> populateProductList(OrderDTO orderDTO) {
    List<Product> products = new ArrayList<>();

    if (!orderDTO.getProductIdList().isEmpty()) {
      for (Long productId : orderDTO.getProductIdList()) {
        Product product =
            productService
                .findProductById(productId)
                .orElseThrow(
                    () ->
                        new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Product with ID " + productId + " not found"));
        products.add(product);
      }
    }

    return products;
  }
}
