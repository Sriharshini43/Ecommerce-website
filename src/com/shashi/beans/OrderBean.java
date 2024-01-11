package com.shashi.beans;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class OrderBean implements Serializable {

    private String transactionId;
    private String productId;
    private int quantity;
    private Double amount;
    private int shipped;
    private Timestamp time;

    public OrderBean() {
        super();
    }

    public OrderBean(String transactionId, String productId, int quantity, Double amount) {
        super();
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.shipped = 0;
        // Initialize the time field when creating a new order
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public OrderBean(String transactionId, String productId, int quantity, Double amount, int shipped) {
        super();
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.shipped = shipped;
        // Initialize the time field when creating a new order
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public Timestamp getTime() {
        return time;
    }

    // Add a setter for the time field
    public void setTime(Timestamp time) {
        this.time = time;
    }
}
