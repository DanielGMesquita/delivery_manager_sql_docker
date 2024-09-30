package org.danielmesquita.repository;

import java.util.Optional;
import org.danielmesquita.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findByCategoryName(String categoryName);
}
