package org.danielmesquita.constants;

public class QueriesSQL {
  public static final String FIND_ALL_PRODUCTS = "SELECT * FROM products";
  public static final String FIND_ALL_ORDERS = "SELECT * FROM orders";
  public static final String INNER_JOIN_TABLES =
      "SELECT * FROM tb_order "
          + " INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
          + " INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id";
}
