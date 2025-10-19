package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidHoursPerDayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CostEstimatorTest {

    @Mock
    private Team mockTeam;

    @Mock
    private ProjectProcess mockProjectProcess;

    @Test
    @DisplayName("estimateCost_validInputs_returnsExpectedCost")
    void estimateCost_validInputs_returnsExpectedCost() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(Float.valueOf(50.0f));
        when(mockTeam.getTeamSize()).thenReturn(Integer.valueOf(5));
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(Integer.valueOf(4));

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 8);

        assertEquals(8000.0f, result);
        verify(mockTeam).getAverageHourlyRate();
        verify(mockTeam).getTeamSize();
        verify(mockProjectProcess).getHoursPerWeek();
    }

    @Test
    @DisplayName("estimateCost_hoursPerDayTooHigh_throwsException")
    void estimateCost_hoursPerDayTooHigh_throwsException() {
        assertThrows(InvalidHoursPerDayException.class, () ->
                CostEstimator.estimateCost(mockTeam, mockProjectProcess, 25)
        );
    }

    @Test
    @DisplayName("estimateCost_hoursPerDayNegative_throwsException")
    void estimateCost_hoursPerDayNegative_throwsException() {
        assertThrows(InvalidHoursPerDayException.class, () ->
                CostEstimator.estimateCost(mockTeam, mockProjectProcess, -3)
        );
    }

    @Test
    @DisplayName("estimateCost_zeroHoursPerDay_returnsZeroCost")
    void estimateCost_zeroHoursPerDay_returnsZeroCost() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(Float.valueOf(100.0f));
        when(mockTeam.getTeamSize()).thenReturn(Integer.valueOf(3));
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(Integer.valueOf(5));

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 0);
        assertEquals(0.0f, result);
    }

    @Test
    @DisplayName("estimateCost_maxValidHours_returnsHighCost")
    void estimateCost_maxValidHours_returnsHighCost() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(Float.valueOf(75.0f));
        when(mockTeam.getTeamSize()).thenReturn(Integer.valueOf(10));
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(Integer.valueOf(5));

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 24);
        assertEquals(90000.0f, result);
    }
}

// Co-generated with AI assistance (GPT-5), reviewed and validated by Alejandro Ramirez