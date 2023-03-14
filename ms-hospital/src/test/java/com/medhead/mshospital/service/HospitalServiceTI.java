package com.medhead.mshospital.service;

import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.repository.BedAvailableProxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HospitalServiceTI {

    @Autowired
    private HospitalService hospitalService;

    @MockBean
    private BedAvailableProxy bedAvailableProxyMock;

    private String speciality_name_test = "Médecine d'urgence";

    @Test
    public void test_getAvailableHospitalsWithSpeciality() {
        when(bedAvailableProxyMock.getBedAvailableByHospitalId(Mockito.anyInt())).thenReturn(1);

        List<Hospital> hospitals = hospitalService.getAvailableHospitalsWithSpeciality(speciality_name_test);

        assertNotNull(hospitals);
    }
}
