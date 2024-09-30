package org.danielmesquita.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.danielmesquita.dto.CategoryDTO;
import org.danielmesquita.entities.Category;
import org.danielmesquita.entities.Product;
import org.danielmesquita.exceptions.ResourceNotFoundException;
import org.danielmesquita.repository.CategoryRepository;
import org.danielmesquita.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  @Autowired CategoryRepository categoryRepository;
  @Autowired ProductRepository productRepository;

  public List<Category> findAllCategories() {
    return categoryRepository.findAll();
  }

  public Optional<Category> findCategoryById(Long id) {
    return categoryRepository.findById(id);
  }

  public Category insertCategory(CategoryDTO categoryDTO) {
    List<Product> products = populateProductList(categoryDTO);

    Category category =
        Category.builder()
            .categoryName(categoryDTO.getName())
            .products(setProductsListToBuild(products))
            .build();
    return categoryRepository.save(category);
  }

  public Category updateCategory(CategoryDTO categoryDTO, Long id) {
    Optional<Category> category = categoryRepository.findById(id);

    if (category.isEmpty()) {
      throw new ResourceNotFoundException("Category not found");
    }

    List<Product> products = populateProductList(categoryDTO);
    Category existingCategory = category.get();
    existingCategory.setProducts(products);
    existingCategory.setCategoryName(categoryDTO.getName());

    return categoryRepository.save(existingCategory);
  }

  public void deleteCategoryById(Long id) {
    categoryRepository.deleteById(id);
  }

  public List<Product> populateProductList(CategoryDTO categoryDTO) {
    List<Product> products = new ArrayList<>();

    if (categoryDTO.getProductIdList() != null && !categoryDTO.getProductIdList().isEmpty()) {
      for (Long productId : categoryDTO.getProductIdList()) {
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

  public List<Product> setProductsListToBuild(List<Product> products) {
    if (products.isEmpty()) {
      return null;
    }

    return products;
  }
}
