package org.danielmesquita.controller;

import java.util.List;
import java.util.Optional;
import org.danielmesquita.dto.ProductDTO;
import org.danielmesquita.entities.Product;
import org.danielmesquita.service.CategoryService;
import org.danielmesquita.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired private ProductService productService;
  @Autowired private CategoryService categoryService;

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
    Product newProduct = productService.insertProduct(productDTO);

    return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.findAllProducts();
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Optional<Product> product = productService.findProductById(id);
    return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id, @RequestBody Product product) {
    product.setId(id);
    productService.updateProduct(product);
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
