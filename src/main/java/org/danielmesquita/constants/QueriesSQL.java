package org.danielmesquita.constants;

public class QueriesSQL {
  public static final String FIND_ALL_PRODUCTS = "SELECT * FROM products";
  public static final String FIND_ALL_ORDERS = "SELECT * FROM orders";
  public static final String INNER_JOIN_TABLES =
      "SELECT * FROM tb_order "
          + " INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
          + " INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id";
  public static final String INSERT_PRODUCTS =
      "INSERT INTO tb_product (name, price, image_uri, description) VALUES (?, ?, ?, ?)";
  public static final String INSERT_ORDER =
      "INSERT INTO tb_order (latitude, longitude, moment, status) VALUES (?, ?, ?, ?)";
  public static final String INSERT_ORDER_PRODUCT =
      "INSERT INTO tb_order_product (order_id, product_id) VALUES (?, ?)";
  public static final String DELETE_ORDER_PRODUCT =
      "DELETE FROM tb_order_product WHERE order_id = ?";
  public static final String DELETE_ORDER = "DELETE FROM tb_order WHERE id = ?";
  public static final String DELETE_PRODUCT = "DELETE FROM tb_product WHERE id = ?";
  public static final String UPDATE_PRODUCT =
      "UPDATE tb_product SET name = ?, price = ?, image_uri = ?, description = ? WHERE id = ?";
    public static final String UPDATE_ORDER =
      "UPDATE tb_order SET latitude = ?, longitude = ?, moment = ?, status = ? WHERE id = ?";
}
