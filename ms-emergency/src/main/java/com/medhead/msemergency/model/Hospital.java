package com.medhead.msemergency.model;

import lombok.Data;

import java.util.List;

@Data
public class Hospital {

    private Integer id;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private List<Speciality> specialties;

    private Integer bedAvaibility;

    private Integer distance = 0;

}