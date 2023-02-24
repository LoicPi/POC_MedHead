package com.medhead.msemergency.model;

import java.util.Set;

import lombok.Data;

@Data
public class Hospital {

    private Integer id;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private Set<Specialist> Specialist;

    private Integer bedAvaibility;

    private Integer distance = 0;

}