package com.food.winterfoodies2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(
        name = "snack"
)
@Getter
@Setter
public class Snack {
    private int ranking;
    @Id
    private String storeId;
    private String name;
    private String address;
    private String phone;
    private double distance;
    private double lat;
    private double lon;

    public Snack(String storeId, String name, String address, String phone, double distance, double lat, double lon) {
        this.storeId = storeId;// 23
        this.name = name;// 24
        this.address = address;// 25
        this.phone = phone;// 26
        this.distance = distance;// 27
        this.lat = lat;// 28
        this.lon = lon;// 29
    }// 30

    public Snack() {
    }// 34


}
