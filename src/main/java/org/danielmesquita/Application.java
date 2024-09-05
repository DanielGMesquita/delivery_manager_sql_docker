package org.danielmesquita;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.danielmesquita.constants.QueriesSQL;
import org.danielmesquita.dbconfig.DB;
import org.danielmesquita.dbconfig.DbException;
import org.danielmesquita.entities.Product;

public class Application {
  public static void main(String[] args) throws SQLException {
    Connection connection = DB.getConnection();

    Statement statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery(QueriesSQL.FIND_ALL_PRODUCTS);

    Map<Long, Product> productMap = new HashMap<>();

    while (resultSet.next()) {
      Long productId = resultSet.getLong("product_id");
      if (productMap.get(productId) == null) {
        Product product = instantiateProduct(resultSet);
        productMap.put(productId, product);
      }

      System.out.println("Product: " + productMap.get(productId));
    }

    Product productToUpdate = new Product();
    productToUpdate.setId(1L); // Supondo que você quer atualizar o produto com ID 1
    productToUpdate.setName("Samsung Galaxy S21");
    productToUpdate.setPrice(2000.0);
    productToUpdate.setDescription("The best Android phone ever");
    productToUpdate.setImageUri("https://www.samsung.com/samsung-galaxy-s21.jpg");

    updateProduct(productToUpdate);

    System.out.println("Product updated: " + productToUpdate);

    Product newProduct = new Product();
    newProduct.setName("Iphone 13");
    newProduct.setPrice(3000.0);
    newProduct.setDescription("The best iPhone ever");
    newProduct.setImageUri("https://apple.com/iphone/4.png");

    insertProduct(newProduct);

    System.out.println("New product inserted: " + newProduct);

    deleteProductById(newProduct.getId());

    System.out.println("Product deleted: " + newProduct.getId());
  }

  private static Product instantiateProduct(ResultSet resultSet) throws SQLException {
    Product product = new Product();
    product.setId(resultSet.getLong("product_id"));
    product.setName(resultSet.getString("name"));
    product.setPrice(resultSet.getDouble("price"));
    product.setDescription(resultSet.getString("description"));
    product.setImageUri(resultSet.getString("image_uri"));
    return product;
  }

  public static void insertProduct(Product product) {
    try (Connection connection = DB.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(
                QueriesSQL.INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, product.getName());
      preparedStatement.setDouble(2, product.getPrice());
      preparedStatement.setString(3, product.getDescription());
      preparedStatement.setString(4, product.getImageUri());

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
          if (resultSet.next()) {
            product.setId(resultSet.getLong(1));
          }
        }
      }
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  public static void deleteProductById(long id) {
    try (Connection connection = DB.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(QueriesSQL.DELETE_PRODUCT)) {

      preparedStatement.setLong(1, id);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }

  public static void updateProduct(Product product) {
    try (Connection connection = DB.getConnection();
        PreparedStatement preparedStatement =
            connection.prepareStatement(QueriesSQL.UPDATE_PRODUCT)) {

      preparedStatement.setString(1, product.getName());
      preparedStatement.setDouble(2, product.getPrice());
      preparedStatement.setString(3, product.getDescription());
      preparedStatement.setString(4, product.getImageUri());
      preparedStatement.setLong(5, product.getId());

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected == 0) {
        throw new DbException("No product found with the given ID: " + product.getId());
      }

    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    }
  }
}
