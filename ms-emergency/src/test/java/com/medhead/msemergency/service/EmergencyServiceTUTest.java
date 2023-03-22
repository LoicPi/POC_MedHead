package com.medhead.msemergency.service;

import com.medhead.msemergency.dto.NearestHospitalReservationTransform;
import com.medhead.msemergency.model.Emergency;
import com.medhead.msemergency.model.Hospital;
import com.medhead.msemergency.model.NearestHospitalReservation;
import com.medhead.msemergency.model.Speciality;
import com.medhead.msemergency.repository.BedAvailableProxy;
import com.medhead.msemergency.repository.EmergencyProxy;
import com.medhead.msemergency.repository.HospitalProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmergencyServiceTUTest {

    @InjectMocks
    private EmergencyService emergencyService;

    @Mock
    private HospitalProxy hospitalProxyMock;

    @Mock
    private BedAvailableProxy bedAvailableProxyMock;

    @Mock
    private EmergencyProxy emergencyProxyMock;

    @Mock
    private NearestHospitalReservationTransform nearestHospitalReservationTransformMock;

    private String speciality_name_test = "Médecine d'urgence";

    private Emergency emergency;

    private String reservation = "123456789";

    private Emergency emergency_test = new Emergency();

    private Hospital hospital_test_0 = new Hospital();

    private Hospital hospital_test_1 = new Hospital();

    private  List<Hospital> hospitals_test = new ArrayList<Hospital>();

    @BeforeEach
    public void setup() {

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
    }

    @Test
    public void getNearestHospitalReservation_Test () {
        NearestHospitalReservation nearestHospitalReservation = new NearestHospitalReservation();

        nearestHospitalReservation.setName(hospital_test_0.getName());
        nearestHospitalReservation.setLatitude(hospital_test_0.getLatitude());
        nearestHospitalReservation.setLongitude(hospital_test_0.getLongitude());
        nearestHospitalReservation.setReservation(reservation);

        when(hospitalProxyMock.getAvailableHospitalsBySpecialist(emergency_test.getSpeciality())).thenReturn(hospitals_test);
        when(emergencyProxyMock.getDistanceBetweenHospitalAndEmergency(emergency_test.getLatitude(), emergency_test.getLongitude(), hospital_test_0.getLatitude(), hospital_test_0.getLongitude())).thenReturn(1000);
        when(emergencyProxyMock.getDistanceBetweenHospitalAndEmergency(emergency_test.getLatitude(), emergency_test.getLongitude(), hospital_test_1.getLatitude(), hospital_test_1.getLongitude())).thenReturn(2000);
        when(bedAvailableProxyMock.savedBedAvailable(hospital_test_0.getId())).thenReturn(reservation);
        when(nearestHospitalReservationTransformMock.transformNearestHospitalToNearestHospitalReservation(hospital_test_0,reservation)).thenReturn(nearestHospitalReservation);

        NearestHospitalReservation nearestHospitalReservation2 = emergencyService.getNearestHospitalReservation(emergency_test);

        assertEquals("Hospital Test 0", nearestHospitalReservation2.getName());
        assertEquals("52.0245842", nearestHospitalReservation2.getLatitude());
        assertEquals("-1.0157426", nearestHospitalReservation2.getLongitude());
        assertEquals("123456789", nearestHospitalReservation2.getReservation());
    }

    @Test
    public void getNearestHospital_Test() throws Exception {
        when(emergencyProxyMock.getDistanceBetweenHospitalAndEmergency(emergency_test.getLatitude(), emergency_test.getLongitude(), hospital_test_0.getLatitude(), hospital_test_0.getLongitude())).thenReturn(1000);
        when(emergencyProxyMock.getDistanceBetweenHospitalAndEmergency(emergency_test.getLatitude(), emergency_test.getLongitude(), hospital_test_1.getLatitude(), hospital_test_1.getLongitude())).thenReturn(2000);

        Hospital nearestHospital = ReflectionTestUtils.invokeMethod(emergencyService, "getNearestHospital", emergency_test, hospitals_test);

        assertEquals("Hospital Test 0", nearestHospital.getName());
        assertEquals("1 test avenue London", nearestHospital.getAddress());
        assertEquals("52.0245842", nearestHospital.getLatitude());
        assertEquals("-1.0157426", nearestHospital.getLongitude());
    }
}
