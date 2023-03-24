package com.medhead.msbedavailable.controller;


import com.medhead.msbedavailable.service.BedAvailableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BedAvailableController.class)
public class BedAvailableControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BedAvailableService bedAvailableServiceMock;

    @Test
    public void test_getBedAvailableByHospitalId() throws Exception {
        when(bedAvailableServiceMock.getBedAvailableByHospitalId(Mockito.anyInt())).thenReturn(2);

        mockMvc.perform(get("/getBedAvailableByHospitalId/{hospitalId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    public void test_savedBedAvailable() throws Exception {
        when(bedAvailableServiceMock.savedBedAvailable(Mockito.anyInt())).thenReturn("1234567890");

        mockMvc.perform(post("/savedBedAvailable/{hospitalId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1234567890));
    }
}
