package models;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private String status;
    private Timestamp dateOrdered;
    private double totalPrice;

    public Order(int orderId, String status, Timestamp dateOrdered, double totalPrice) {
        this.orderId = orderId;
        this.status = status;
        this.dateOrdered = dateOrdered;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Timestamp dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                ", dateOrdered=" + dateOrdered +
                ", totalPrice=" + totalPrice +
                '}';
    }
}