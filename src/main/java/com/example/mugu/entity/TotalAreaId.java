package com.example.mugu.entity;

import java.io.Serializable;
import java.util.Objects;

public class TotalAreaId implements Serializable {
    private String city;
    private String county;
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalAreaId that = (TotalAreaId) o;
        return Objects.equals(city, that.city) && Objects.equals(county, that.county) && Objects.equals(town, that.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, county, town);
    }
}