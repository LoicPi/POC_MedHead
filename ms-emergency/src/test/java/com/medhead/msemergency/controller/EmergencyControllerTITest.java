package com.medhead.msemergency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.medhead.msemergency.dto.NearestHospitalReservationTransform;
import com.medhead.msemergency.model.Emergency;
import com.medhead.msemergency.model.Hospital;
import com.medhead.msemergency.model.NearestHospitalReservation;
import com.medhead.msemergency.model.Speciality;
import com.medhead.msemergency.repository.AuthorizationProxy;
import com.medhead.msemergency.repository.BedAvailableProxy;
import com.medhead.msemergency.repository.EmergencyProxy;
import com.medhead.msemergency.repository.HospitalProxy;
import com.medhead.msemergency.service.EmergencyService;

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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {EmergencyController.class, EmergencyService.class})
@WebMvcTest
public class EmergencyControllerTITest {

    private final static String TEST_USER_ID = "user-id-123";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BedAvailableProxy bedAvailableProxyMock;

    @MockBean
    private EmergencyProxy emergencyProxyMock;

    @MockBean
    private HospitalProxy hospitalProxyMock;

    @MockBean
    private NearestHospitalReservationTransform nearestHospitalReservationTransform;

    @MockBean
    private AuthorizationProxy authorizationProxyMock;

    private String speciality_name_test = "Médecine d'urgence";

    private String reservation = "123456789";

    private Emergency emergency_test = new Emergency();

    private Hospital hospital_test_0 = new Hospital();

    private Hospital hospital_test_1 = new Hospital();

    private List<Hospital> hospitals_test = new ArrayList<Hospital>();

    private NearestHospitalReservation nearestHospitalReservation = new NearestHospitalReservation();

    @BeforeEach
    public void test_setup() {
        Speciality speciality_test_0 = new Speciality();

        Speciality speciality_test_1 = new Speciality();

        Speciality speciality_test_2 = new Speciality();

        Speciality speciality_test_3 = new Speciality();

        List < Speciality> specialities_0 = new ArrayList<Speciality>();

        List < Speciality> specialities_1 = new ArrayList<Speciality>();

        emergency_test.setLatitude("51.507351");
        emergency_test.setLongitude("-0.127758");
        emergency_test.setSpeciality(speciality_name_test);

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

        hospitals_test.add(hospital_test_0);
        hospitals_test.add(hospital_test_1);

        nearestHospitalReservation.setName("Hospital Test 0");
        nearestHospitalReservation.setLatitude("52.0245842");
        nearestHospitalReservation.setLongitude("-1.0157426");
        nearestHospitalReservation.setReservation(reservation);
    }

    @Test
    public void test_getEmergency_user_authenticate() throws Exception {
        when(hospitalProxyMock.getAvailableHospitalsBySpecialist(speciality_name_test)).thenReturn(hospitals_test);
        when(emergencyProxyMock.getDistanceBetweenHospitalAndEmergency(emergency_test.getLatitude(), emergency_test.getLongitude(), hospital_test_0.getLatitude(), hospital_test_0.getLongitude())).thenReturn(1000);
        when(emergencyProxyMock.getDistanceBetweenHospitalAndEmergency(emergency_test.getLatitude(), emergency_test.getLongitude(), hospital_test_1.getLatitude(), hospital_test_1.getLongitude())).thenReturn(2000);
        when(bedAvailableProxyMock.savedBedAvailable(hospital_test_0.getId())).thenReturn(reservation);
        when(nearestHospitalReservationTransform.transformNearestHospitalToNearestHospitalReservation(hospital_test_0, reservation)).thenReturn(nearestHospitalReservation);

        mockMvc.perform(get("/emergency")
                        .with(user(TEST_USER_ID))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emergency_test))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hospital Test 0"))
                .andExpect(jsonPath("$.latitude").value("52.0245842"))
                .andExpect(jsonPath("$.longitude").value("-1.0157426"))
                .andExpect(jsonPath("$.reservation").value("123456789"));
    }

    @Test
    public void test_getEmergency_user_not_authenticate() throws Exception {
        mockMvc.perform(get("/emergency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emergency_test))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
