package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidHoursPerDayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private CostEstimator mockEstimator;

    @Mock
    private Team mockTeam;

    @Mock
    private ProjectProcess mockProcess;

    @InjectMocks
    private BudgetService budgetService;

    @Test
    @DisplayName("isBudgetFeasible_estimatedCostBelowMax_returnsTrue")
    void isBudgetFeasible_estimatedCostBelowMax_returnsTrue() throws InvalidHoursPerDayException {
        when(mockEstimator.estimateCost(mockTeam, mockProcess, 8)).thenReturn(5000f);
        when(mockProcess.getHoursPerWeek()).thenReturn(40); // ✅ int, not float
        when(mockTeam.getTeamSize()).thenReturn(5);
        when(mockTeam.getAverageHourlyRate()).thenReturn(50.0f);

        boolean result = budgetService.isBudgetFeasible(mockTeam, mockProcess, 8, 6000f);

        assertTrue(result);
        verify(mockEstimator).estimateCost(mockTeam, mockProcess, 8);
    }

    @Test
    @DisplayName("isBudgetFeasible_estimatedCostAboveMax_returnsFalse")
    void isBudgetFeasible_estimatedCostAboveMax_returnsFalse() throws InvalidHoursPerDayException {
        when(mockEstimator.estimateCost(mockTeam, mockProcess, 8)).thenReturn(9000f);
        when(mockProcess.getHoursPerWeek()).thenReturn(40); // ✅ int
        when(mockTeam.getTeamSize()).thenReturn(5);
        when(mockTeam.getAverageHourlyRate()).thenReturn(50.0f);

        boolean result = budgetService.isBudgetFeasible(mockTeam, mockProcess, 8, 6000f);

        assertFalse(result);
    }

    @Test
    @DisplayName("isBudgetFeasible_estimatorThrowsException_returnsFalse")
    void isBudgetFeasible_estimatorThrowsException_returnsFalse() throws InvalidHoursPerDayException {
        when(mockEstimator.estimateCost(mockTeam, mockProcess, 25))
                .thenThrow(new InvalidHoursPerDayException("Too many hours"));

        boolean result = budgetService.isBudgetFeasible(mockTeam, mockProcess, 25, 10000f);

        assertFalse(result);
        verify(mockEstimator).estimateCost(mockTeam, mockProcess, 25);
    }
}
