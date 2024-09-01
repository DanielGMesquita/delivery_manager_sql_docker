package org.danielmesquita;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.danielmesquita.dbconfig.DB;

public class Application {
  public static void main(String[] args) throws SQLException {
    Connection connection = DB.getConnection();

    Statement statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery("select * from tb_product");

    while (resultSet.next()) {
      System.out.println(
          resultSet.getInt("id")
              + ", "
              + resultSet.getString("name")
              + ", "
              + resultSet.getDouble("price"));
    }
  }
}
