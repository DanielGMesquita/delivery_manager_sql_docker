package org.danielmesquita.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "products")
@Data
@Builder
@ToString(exclude = "orders")
@EqualsAndHashCode(exclude = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private long id;

  @Column(name = "product_name")
  private String name;

  @Column(name = "product_price")
  private Double price;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  @JsonBackReference
  private Category category;

  @ManyToMany
  @JoinTable(
          name = "item_order",
          joinColumns = @JoinColumn(name = "product_id"),
          inverseJoinColumns = @JoinColumn(name = "order_id"))
  Set<Order> orders;
}
