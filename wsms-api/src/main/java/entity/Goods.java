package entity;

import java.io.Serializable;

public class Goods implements Serializable {
    private int id;
    private String batchNumber;
    private String kind;
    private String name;
    private int number;
    private String firstInTime;
    private int status;
    private int orderNumber;
    private int shelfNumber;
    private int deliveryOrderNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFirstInTime() {
        return firstInTime;
    }

    public void setFirstInTime(String firstInTime) {
        this.firstInTime = firstInTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public int getDeliveryOrderNumber() {
        return deliveryOrderNumber;
    }

    public void setDeliveryOrderNumber(int deliveryOrderNumber) {
        this.deliveryOrderNumber = deliveryOrderNumber;
    }
}
