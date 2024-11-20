package delivery.reports;

public class Order {
    private final String item;
    private final Double value;
    private final String client;

    public Order(String item, Double value, String client) {
        this.item = item;
        this.value = value;
        this.client = client;
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
}