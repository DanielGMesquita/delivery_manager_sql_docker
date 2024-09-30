package org.danielmesquita.service;

import java.util.List;
import java.util.Optional;
import org.danielmesquita.entities.Category;
import org.danielmesquita.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  @Autowired CategoryRepository categoryRepository;

  public List<Category> findAllCategories() {
    return categoryRepository.findAll();
  }

  public Optional<Category> findCategoryById(Long id) {
    return categoryRepository.findById(id);
  }

  public Optional<Category> findCategoryByName(String categoryName) {
    return categoryRepository.findByCategoryName(categoryName);
  }

  public Category insertCategory(Category category) {
    return categoryRepository.save(category);
  }

  public void deleteCategoryById(Long id) {
    categoryRepository.deleteById(id);
  }
}
