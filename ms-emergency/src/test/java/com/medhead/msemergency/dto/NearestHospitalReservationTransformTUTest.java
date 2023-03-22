package com.medhead.msemergency.dto;

import com.medhead.msemergency.model.Hospital;
import com.medhead.msemergency.model.NearestHospitalReservation;
import com.medhead.msemergency.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NearestHospitalReservationTransformTUTest {

    @InjectMocks
    private NearestHospitalReservationTransform nearestHospitalReservationTransform;

    private Hospital hospital_test_0 = new Hospital();

    private String reservation = "123456789";

    @BeforeEach
    public void setup() {

        Speciality speciality_test_0 = new Speciality();

        Speciality speciality_test_1 = new Speciality();

        Speciality speciality_test_2 = new Speciality();

        List< Speciality> specialities_0 = new ArrayList<Speciality>();

        speciality_test_0.setId(17);
        speciality_test_0.setName("Médecine d'urgence");

        speciality_test_1.setId(11);
        speciality_test_1.setName("Orthodontie");

        speciality_test_2.setId(21);
        speciality_test_2.setName("Cardiologie");

        specialities_0.add(speciality_test_0);
        specialities_0.add(speciality_test_1);
        specialities_0.add(speciality_test_2);

        hospital_test_0.setId(1);
        hospital_test_0.setName("Hospital Test 0");
        hospital_test_0.setAddress("1 test avenue London");
        hospital_test_0.setLatitude("52.0245842");
        hospital_test_0.setLongitude("-1.0157426");
        hospital_test_0.setSpecialties(specialities_0);
    }

    @Test
    public void transformNearestHospitalToNearestHospitalReservation_Test() {

        NearestHospitalReservation nearestHospitalReservation = nearestHospitalReservationTransform.transformNearestHospitalToNearestHospitalReservation(hospital_test_0, reservation);

        assertEquals("Hospital Test 0", nearestHospitalReservation.getName());
        assertEquals("123456789", nearestHospitalReservation.getReservation());
        assertEquals("52.0245842", nearestHospitalReservation.getLatitude());
        assertEquals("-1.0157426", nearestHospitalReservation.getLongitude());
    }
}
