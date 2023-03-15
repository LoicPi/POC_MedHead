package com.medhead.msbedavailable.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BedAvailableServiceTU {

    @InjectMocks
    private BedAvailableService bedAvailableService;

    @Test
    public void test_getBedAvailableByHospitalId() {

        Integer bedAvailable = bedAvailableService.getBedAvailableByHospitalId(1);

        Integer lengthOfResult = String.valueOf(bedAvailable).length();

        assertNotNull(bedAvailable, "BedAvailable exist");
        assertSame(bedAvailable.getClass(), Integer.class, "La classe du résultat est bien un Integer");
        assertEquals(lengthOfResult, 1, "Le résultat a bien une longeur de 9");
        assertTrue(0 <= bedAvailable && bedAvailable < 6 , "Le résultat est bien compris entre 0 et 1000000000");
    }

    @Test
    public void test_savedBedAvailable() {

        String savedBedAvailable = bedAvailableService.savedBedAvailable(1);

        Integer lengthOfResult = savedBedAvailable.length();

        Integer resultInteger = Integer.valueOf(savedBedAvailable);

        assertNotNull(savedBedAvailable, "BedAvailable is saved");
        assertSame(savedBedAvailable.getClass(), String.class, "La classe du résultat est bien un String");
        assertEquals(lengthOfResult, 9, "Le résultat a bien une longeur de 1");
        assertTrue(0 < resultInteger && resultInteger < 1000000000 , "Le résultat est bien compris entre 0 et 1000000000");
    }

}
