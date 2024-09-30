package org.danielmesquita.service;

import java.util.List;
import java.util.Optional;
import org.danielmesquita.dto.ProductDTO;
import org.danielmesquita.entities.Category;
import org.danielmesquita.entities.Product;
import org.danielmesquita.exceptions.ResourceNotFoundException;
import org.danielmesquita.repository.CategoryRepository;
import org.danielmesquita.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired private ProductRepository productRepository;
  @Autowired private CategoryRepository categoryRepository;

  public Product insertProduct(ProductDTO productDTO) {
    Category category =
        categoryRepository
            .findByCategoryName(productDTO.getCategory())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    Product product =
        Product.builder()
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .category(category)
            .build();
    return productRepository.save(product);
  }

  public Optional<Product> findProductById(Long id) {
    return productRepository.findById(id);
  }

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public void updateProduct(Product product) {
    productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
