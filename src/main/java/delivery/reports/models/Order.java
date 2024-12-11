package delivery.reports.models;

public class Order {
    private final String item;
    private final Double value;
    private final String client;
    private final String restaurant;
    private final String status;

    public Order(String item, Double value, String client) {
        this.item = item;
        this.value = value;
        this.client = client;
        this.restaurant = null;
        this.status = null;
    }

    public Order(String item, Double value, String client, String restaurant, String status) {
        this.item = item;
        this.value = value;
        this.client = client;
        this.restaurant = restaurant;
        this.status = status;
    }

    public String getItem() {
        return item;
    }

    public Double getValue() {
        return value;
    }

    public String getClient() {
        return client;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getStatus() {
        return status;
    }
}