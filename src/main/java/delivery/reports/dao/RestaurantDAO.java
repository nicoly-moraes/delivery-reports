package delivery.reports.dao;

import delivery.reports.database.DatabaseConnection;
import delivery.reports.models.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    public static List<Restaurant> findAll() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM restaurants";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Restaurant> list = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Restaurant restaurant = new Restaurant(id, name);
                list.add(restaurant);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}