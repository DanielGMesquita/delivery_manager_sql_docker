package org.danielmesquita.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "category")
@Data
@Builder
@ToString(exclude = "products")
@EqualsAndHashCode(exclude = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column(name = "category_name", nullable = false)
  private String categoryName;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Product> products;
}
