package org.danielmesquita;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.danielmesquita.constants.QueriesSQL;
import org.danielmesquita.dbconfig.DB;
import org.danielmesquita.entities.Order;
import org.danielmesquita.entities.OrderStatus;
import org.danielmesquita.entities.Product;

public class Application {
  public static void main(String[] args) throws SQLException {
    Connection connection = DB.getConnection();

    Statement statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery(QueriesSQL.INNER_JOIN_TABLES);

    Map<Long, Order> orderMap = new HashMap<>();

    Map<Long, Product> productMap = new HashMap<>();

    while (resultSet.next()) {
      Long orderId = resultSet.getLong("order_id");
      if (orderMap.get(orderId) == null) {
        Order order = instantiateOrder(resultSet);
        orderMap.put(orderId, order);
      }

      Long productId = resultSet.getLong("product_id");
      if (productMap.get(productId) == null) {
        Product product = instantiateProduct(resultSet);
        productMap.put(productId, product);
      }

      orderMap.get(orderId).getProducts().add(productMap.get(productId));
    }

    for (Long orderId : orderMap.keySet()) {
      System.out.println(orderMap.get(orderId));
      for (Product product : orderMap.get(orderId).getProducts()) {
        System.out.println(product);
      }
      System.out.println();
    }
  }

  private static Product instantiateProduct(ResultSet resultSet) throws SQLException {
    Product product = new Product();
    product.setId(
        resultSet.getLong(
            "product_id")); // product_id instead of id to avoid conflict with Order id
    product.setName(resultSet.getString("name"));
    product.setPrice(resultSet.getDouble("price"));
    product.setDescription(resultSet.getString("description"));
    product.setImageUri(resultSet.getString("image_uri"));
    return product;
  }

  private static Order instantiateOrder(ResultSet resultSet) throws SQLException {
    Order order = new Order();
    order.setId(
        resultSet.getLong("order_id")); // order_id instead of id to avoid conflict with Product id
    order.setLatitude(resultSet.getDouble("latitude"));
    order.setLongitude(resultSet.getDouble("longitude"));
    order.setMoment(resultSet.getTimestamp("moment").toInstant());
    order.setStatus(OrderStatus.values()[resultSet.getInt("status")]);
    return order;
  }
}
