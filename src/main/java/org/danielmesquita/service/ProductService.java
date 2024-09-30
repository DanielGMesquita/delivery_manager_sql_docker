package org.danielmesquita.service;

import java.util.List;
import java.util.Optional;
import org.danielmesquita.entities.Product;
import org.danielmesquita.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired private ProductRepository productRepository;

  public void insertProduct(Product product) {
    productRepository.save(product);
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
