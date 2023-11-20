package com.example.mugu.controller;

import com.example.mugu.repository.TotalAreaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {
    private final TotalAreaRepository totalAreaRepository;

    // 생성자 주입
    public AddressController(TotalAreaRepository totalAreaRepository) {
        this.totalAreaRepository = totalAreaRepository;
    }

    @GetMapping("/cities")
    public List<String> getCities() {
        return totalAreaRepository.findDistinctCity();
    }

    @GetMapping("/counties")
    public List<String> getCounties(@RequestParam String city) {
        return totalAreaRepository.findCountyByCity(city);
    }

    @GetMapping("/towns")
    public List<String> getTowns(@RequestParam String city, @RequestParam String county) {
        return totalAreaRepository.findTownByCityAndCounty(city, county);
    }
}
