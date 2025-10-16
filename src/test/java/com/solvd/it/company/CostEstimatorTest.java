package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidHoursPerDayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CostEstimatorTest {

    @Mock
    private Team mockTeam;

    @Mock
    private ProjectProcess mockProjectProcess;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("estimateCost_validInput_correctCalculation")
    void estimateCost_validInput_correctCalculation() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(50.0f);
        when(mockTeam.getTeamSize()).thenReturn(4);
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(5);

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 8);

        // totalHours = 4 * 8 * 5 = 160
        // expected cost = 50 * 160 = 8000
        assertEquals(8000.0f, result, 0.001f);
    }

    @Test
    @DisplayName("estimateCost_zeroHoursPerDay_returnsZero")
    void estimateCost_zeroHoursPerDay_returnsZero() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(100.0f);
        when(mockTeam.getTeamSize()).thenReturn(3);
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(10);

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 0);

        // expected cost = 0 since hoursPerDay = 0
        assertEquals(0.0f, result, 0.001f);
    }

    @Test
    @DisplayName("estimateCost_hoursPerDayExceedsLimit_throwsException")
    void estimateCost_hoursPerDayExceedsLimit_throwsException() {
        assertThrows(InvalidHoursPerDayException.class, () ->
                CostEstimator.estimateCost(mockTeam, mockProjectProcess, 25)
        );
    }

    @Test
    @DisplayName("estimateCost_negativeHoursPerDay_throwsException")
    void estimateCost_negativeHoursPerDay_throwsException() {
        assertThrows(InvalidHoursPerDayException.class, () ->
                CostEstimator.estimateCost(mockTeam, mockProjectProcess, -5)
        );
    }

    @Test
    @DisplayName("estimateCost_boundaryHoursPerDay24_validResult")
    void estimateCost_boundaryHoursPerDay24_validResult() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(40.0f);
        when(mockTeam.getTeamSize()).thenReturn(5);
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(7);

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 24);

        // totalHours = 5 * 24 * 7 = 840
        // expected cost = 40 * 840 = 33600
        assertEquals(33600.0f, result, 0.001f);
    }

    @Test
    @DisplayName("estimateCost_nullDependencies_throwsNullPointer")
    void estimateCost_nullDependencies_throwsNullPointer() {
        assertThrows(NullPointerException.class, () ->
                CostEstimator.estimateCost(null, null, 8)
        );
    }
}
