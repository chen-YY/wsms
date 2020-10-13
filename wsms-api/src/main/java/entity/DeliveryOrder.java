package entity;

import java.io.Serializable;

public class DeliveryOrder implements Serializable {
    private int id;
    private String receiveDep;
    private int receiveUser;
    private int sorter;
    private String uuid;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiveDep() {
        return receiveDep;
    }

    public void setReceiveDep(String receiveDep) {
        this.receiveDep = receiveDep;
    }

    public int getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(int receiveUser) {
        this.receiveUser = receiveUser;
    }

    public int getSorter() {
        return sorter;
    }

    public void setSorter(int sorter) {
        this.sorter = sorter;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
