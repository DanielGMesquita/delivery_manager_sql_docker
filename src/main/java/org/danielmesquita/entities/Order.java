package org.danielmesquita.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@ToString(exclude = "products")
@EqualsAndHashCode(exclude = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @ManyToMany List<Product> products;
}
