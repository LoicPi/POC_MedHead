package com.medhead.mshospital.controller;

import com.medhead.mshospital.model.Speciality;
import com.medhead.mshospital.model.Hospital;
import com.medhead.mshospital.repository.BedAvailableProxy;
import com.medhead.mshospital.repository.HospitalRepository;
import com.medhead.mshospital.repository.SpecialityRepository;
import com.medhead.mshospital.service.HospitalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {HospitalController.class, HospitalService.class})
@WebMvcTest
public class HospitalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalRepository hospitalRepositoryMock;

    @MockBean
    private SpecialityRepository specialityRepositoryMock;

    @MockBean
    private BedAvailableProxy bedAvailableProxyMock;

    private String speciality_name_test = "Mock";

    private Speciality speciality_test_0 = new Speciality();

    private Speciality speciality_test_1 = new Speciality();

    private Speciality speciality_test_2 = new Speciality();

    private Speciality speciality_test_3 = new Speciality();

    private List< Speciality> specialities_0 = new ArrayList<Speciality>();

    private List < Speciality> specialities_1 = new ArrayList<Speciality>();

    private List < Speciality> specialities_2 = new ArrayList<Speciality>();

    private List < Speciality> specialities_3 = new ArrayList<Speciality>();

    private Hospital hospital_test_0 = new Hospital();

    private Hospital hospital_test_1 = new Hospital();

    private Hospital hospital_test_2 = new Hospital();

    private Hospital hospital_test_3 = new Hospital();

    private List<Hospital> hospitals_test = new ArrayList<Hospital>();

    @BeforeEach
    public void test_setup() {
        speciality_test_0.setId(17);
        speciality_test_0.setName(speciality_name_test);

        speciality_test_1.setId(11);
        speciality_test_1.setName("Orthodontie");

        speciality_test_2.setId(21);
        speciality_test_2.setName("Cardiologie");

        speciality_test_3.setId(37);
        speciality_test_3.setName("Médecine du travail");

        specialities_0.add(speciality_test_0);
        specialities_0.add(speciality_test_1);
        specialities_0.add(speciality_test_2);

        specialities_1.add(speciality_test_0);
        specialities_1.add(speciality_test_3);

        specialities_2.add(speciality_test_1);

        specialities_3.add(speciality_test_0);

        hospital_test_0.setId(1);
        hospital_test_0.setName("Hospital Test 0");
        hospital_test_0.setAddress("1 test avenue London");
        hospital_test_0.setLatitude("52.0245842");
        hospital_test_0.setLongitude("-1.0157426");
        hospital_test_0.setSpecialties(specialities_0);

        hospital_test_1.setId(2);
        hospital_test_1.setName("Hospital Test 1");
        hospital_test_1.setAddress("3 test avenue 2 London");
        hospital_test_1.setLatitude("53.0247512");
        hospital_test_1.setLongitude("-0.020085");
        hospital_test_1.setSpecialties(specialities_1);

        hospital_test_2.setId(3);
        hospital_test_2.setName("Hospital Test 2");
        hospital_test_2.setAddress("7 test avenue 5 London");
        hospital_test_2.setLatitude("51.517623");
        hospital_test_2.setLongitude("-1.57623398");
        hospital_test_2.setSpecialties(specialities_2);

        hospital_test_3.setId(4);
        hospital_test_3.setName("Hospital Test 3");
        hospital_test_3.setAddress("10 test road 85 London");
        hospital_test_3.setLatitude("49.4756239");
        hospital_test_3.setLongitude("-1.548139");
        hospital_test_3.setSpecialties(specialities_3);

        hospitals_test.add(hospital_test_0);
        hospitals_test.add(hospital_test_1);
        hospitals_test.add(hospital_test_2);
        hospitals_test.add(hospital_test_3);
    }

    @Test
    public void test_getAvailableHospitalsWithSpeciality() throws Exception {
        when(hospitalRepositoryMock.getHospitals()).thenReturn(hospitals_test);
        when(bedAvailableProxyMock.getBedAvailableByHospitalId(1)).thenReturn(2);
        when(bedAvailableProxyMock.getBedAvailableByHospitalId(2)).thenReturn(0);
        when(bedAvailableProxyMock.getBedAvailableByHospitalId(3)).thenReturn(1);
        when(bedAvailableProxyMock.getBedAvailableByHospitalId(4)).thenReturn(3);

        mockMvc.perform(get("/availablehospitalswithspecialist/{specialityName}",  speciality_name_test)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]['name']").value("Hospital Test 0"));
    }
}
