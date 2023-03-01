package com.medhead.msemergency.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Hospital {

    private Integer id;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private ArrayList<Speciality> specialties;

    private Integer bedAvaibility;

    private Integer distance = 0;

}