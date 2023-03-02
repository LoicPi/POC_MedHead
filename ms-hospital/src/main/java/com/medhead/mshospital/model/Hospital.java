package com.medhead.mshospital.model;

import java.util.List;

import lombok.Data;

@Data
public class Hospital {

    private Integer id;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private List<Speciality> specialties;
    
    private Integer bedAvailable;

}
