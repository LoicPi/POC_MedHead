package com.medhead.msemergency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.medhead.msemergency.model.Emergency;
import com.medhead.msemergency.model.NearestHospitalReservation;
import com.medhead.msemergency.repository.AuthorizationProxy;
import com.medhead.msemergency.service.EmergencyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmergencyController.class)
public class EmergencyControllerUnitTest {

    private final static String TEST_USER_ID = "user-id-123";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmergencyService emergencyServiceMock;

    @MockBean
    private AuthorizationProxy authorizationProxyMock;
    private Emergency emergency_test = new Emergency();

    private NearestHospitalReservation nearestHospitalReservation = new NearestHospitalReservation();

    @BeforeEach
    public void setup_test() {
        String speciality_name_test = "Médecine d'urgence";

        emergency_test.setLatitude("51.507351");
        emergency_test.setLongitude("-0.127758");
        emergency_test.setSpeciality(speciality_name_test);

        nearestHospitalReservation.setName("Hospital Test 0");
        nearestHospitalReservation.setLatitude("52.0245842");
        nearestHospitalReservation.setLongitude("-1.0157426");
        nearestHospitalReservation.setReservation("123456789");
    }

    @Test
    public void test_getEmergency_user_authenticate() throws Exception {
        when(emergencyServiceMock.getNearestHospitalReservation(emergency_test)).thenReturn(nearestHospitalReservation);

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
