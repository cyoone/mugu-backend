package com.example.mugu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "total_area")  // DB 테이블 이름과 매핑
@IdClass(TotalAreaId.class)
public class TotalArea {
    // DB 테이블의 컬럼명과 매핑
    @Id
    private String city;

    @Id
    private String county;

    @Id
    private String town;

    // getters
    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    // setters
    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setTown(String town) {
        this.town = town;
    }
}