package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidHoursPerDayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @BeforeEach
    void setUp() {
        reset(mockTeam, mockProjectProcess);
    }

    @Test
    @DisplayName("estimateCost_validInput_correctCalculationAndVerification")
    void estimateCost_validInput_correctCalculationAndVerification() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(50.0f);
        when(mockTeam.getTeamSize()).thenReturn(4);
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(5);

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 8);

        assertEquals(8000.0f, result, 0.001f);

        verify(mockTeam, times(1)).getAverageHourlyRate();
        verify(mockTeam, times(1)).getTeamSize();
        verify(mockProjectProcess, times(1)).getHoursPerWeek();
    }

    @Test
    @DisplayName("estimateCost_boundaryHoursPerDay24_validResultAndInteractions")
    void estimateCost_boundaryHoursPerDay24_validResultAndInteractions() throws InvalidHoursPerDayException {
        when(mockTeam.getAverageHourlyRate()).thenReturn(40.0f);
        when(mockTeam.getTeamSize()).thenReturn(5);
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(7);

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, 24);

        assertEquals(33600.0f, result, 0.001f);
        verify(mockTeam, atLeastOnce()).getAverageHourlyRate();
        verify(mockProjectProcess, atLeastOnce()).getHoursPerWeek();
    }

    @Test
    @DisplayName("estimateCost_hoursPerDayExceedsLimit_throwsException")
    void estimateCost_hoursPerDayExceedsLimit_throwsException() {
        assertThrows(InvalidHoursPerDayException.class, () ->
                CostEstimator.estimateCost(mockTeam, mockProjectProcess, 25)
        );
        verifyNoInteractions(mockTeam, mockProjectProcess);
    }

    @Test
    @DisplayName("estimateCost_negativeHoursPerDay_throwsException")
    void estimateCost_negativeHoursPerDay_throwsException() {
        assertThrows(InvalidHoursPerDayException.class, () ->
                CostEstimator.estimateCost(mockTeam, mockProjectProcess, -5)
        );
        verifyNoInteractions(mockTeam, mockProjectProcess);
    }

    @ParameterizedTest(name = "hoursPerDay={0}, expectedCost={1}")
    @CsvSource({
            "1, 1000.0",
            "2, 2000.0",
            "4, 4000.0"
    })
    @DisplayName("estimateCost_parameterizedValidInputs_computesExpectedCost")
    void estimateCost_parameterizedValidInputs_computesExpectedCost(int hoursPerDay, float expectedCost)
            throws InvalidHoursPerDayException {

        when(mockTeam.getAverageHourlyRate()).thenReturn(50.0f);
        when(mockTeam.getTeamSize()).thenReturn(5);
        when(mockProjectProcess.getHoursPerWeek()).thenReturn(4);

        float result = CostEstimator.estimateCost(mockTeam, mockProjectProcess, hoursPerDay);

        assertEquals(expectedCost, result, 0.001f);
    }

    @Test
    @DisplayName("estimateCost_nullDependencies_throwsNullPointer")
    void estimateCost_nullDependencies_throwsNullPointer() {
        assertThrows(NullPointerException.class, () ->
                CostEstimator.estimateCost(null, null, 8)
        );
    }
}

// Co-generated with AI assistance (GPT-5), reviewed and validated by Alejandro Ramirez
