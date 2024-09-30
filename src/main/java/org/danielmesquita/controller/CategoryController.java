package org.danielmesquita.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.danielmesquita.dto.CategoryDTO;
import org.danielmesquita.entities.Category;
import org.danielmesquita.entities.Product;
import org.danielmesquita.service.CategoryService;
import org.danielmesquita.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  @Autowired private CategoryService categoryService;
  @Autowired private ProductService productService;

  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
    List<Product> products = populateProductList(categoryDTO);

    Category category =
        Category.builder()
            .categoryName(categoryDTO.getName())
            .products(setProductsListToBuild(products))
            .build();

    categoryService.insertCategory(category);

    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.findAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    Optional<Category> category = categoryService.findCategoryById(id);
    return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Category> updateCategory(
      @PathVariable Long id, @RequestBody Category categoryDetails) {
    Optional<Category> category = categoryService.findCategoryById(id);
    if (category.isPresent()) {
      Category existingCategory = category.get();
      existingCategory.setCategoryName(categoryDetails.getCategoryName());
      categoryService.insertCategory(existingCategory);
      return new ResponseEntity<>(existingCategory, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategoryById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public List<Product> populateProductList(CategoryDTO categoryDTO) {
    List<Product> products = new ArrayList<>();

    if (categoryDTO.getProductIdList() != null && !categoryDTO.getProductIdList().isEmpty()) {
      for (Long productId : categoryDTO.getProductIdList()) {
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

  public List<Product> setProductsListToBuild(List<Product> products) {
    if (products.isEmpty()) {
      return null;
    }

    return products;
  }
}
