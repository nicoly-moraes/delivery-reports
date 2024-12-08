package delivery.reports.dao;

import delivery.reports.database.DatabaseConnection;
import delivery.reports.models.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public static List<Order> findByRestaurantAndInterval(Long restaurant_id, LocalDate startDate, LocalDate endDate) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = """
                        SELECT\s
                            oi.order_id,
                            oi.product_id,
                            p.name AS product_name,
                            SUM(oi.quantity) AS total_quantity,
                            SUM(oi.price * oi.quantity) AS total_price,
                            c.name AS customer_name
                        FROM\s
                            public.order_items oi
                        JOIN\s
                            public.products p ON oi.product_id = p.id
                        JOIN\s
                            public.orders o ON oi.order_id = o.id
                        JOIN\s
                            public.customers c ON o.customer_id = c.id
                        WHERE\s
                            o.restaurant_id = %d
                                AND o.created_at >= '%s'
                                AND o.created_at <= '%s'
                        GROUP BY\s
                            oi.order_id, oi.product_id, p.name, c.name
                        ORDER BY\s
                            oi.order_id, p.name;
                       """.formatted(restaurant_id, startDate, endDate);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Order> list = new ArrayList<>();
            while (resultSet.next()) {
                String item = resultSet.getString("product_name");
                Double value = resultSet.getDouble("total_price");
                String client = resultSet.getString("customer_name");
                Order order = new Order(item, value, client);
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}