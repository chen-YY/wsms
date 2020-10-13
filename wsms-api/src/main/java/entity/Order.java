package entity;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String sponsorDep;
    private int sponsorUser;
    private String disposeDep;
    private int disposeUser;
    private String status;
    private String starTime;
    private String endTime;
    private String type;
    private String remark;
    private String goods;
    private int number;
    private String only;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSponsorDep() {
        return sponsorDep;
    }

    public void setSponsorDep(String sponsorDep) {
        this.sponsorDep = sponsorDep;
    }

    public int getSponsorUser() {
        return sponsorUser;
    }

    public void setSponsorUser(int sponsorUser) {
        this.sponsorUser = sponsorUser;
    }

    public String getDisposeDep() {
        return disposeDep;
    }

    public void setDisposeDep(String disposeDep) {
        this.disposeDep = disposeDep;
    }

    public int getDisposeUser() {
        return disposeUser;
    }

    public void setDisposeUser(int disposeUser) {
        this.disposeUser = disposeUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOnly() {
        return only;
    }

    public void setOnly(String only) {
        this.only = only;
    }
}
