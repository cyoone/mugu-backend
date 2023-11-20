package com.example.mugu.repository;

import com.example.mugu.entity.TotalArea;
import com.example.mugu.entity.TotalAreaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TotalAreaRepository extends JpaRepository<TotalArea, TotalAreaId> {
    @Query("SELECT DISTINCT city FROM TotalArea")
    List<String> findDistinctCity();

    @Query("SELECT DISTINCT county FROM TotalArea WHERE city = ?1")
    List<String> findCountyByCity(String city);

    @Query("SELECT DISTINCT town FROM TotalArea WHERE city = ?1 AND county = ?2")
    List<String> findTownByCityAndCounty(String city, String county);
}

