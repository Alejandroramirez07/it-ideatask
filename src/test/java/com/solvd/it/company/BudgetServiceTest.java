package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidHoursPerDayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    private final BudgetService budgetService = new BudgetService(new CostEstimator());

    @Mock
    private Team mockTeam;

    @Mock
    private ProjectProcess mockProcess;

    @Test
    @DisplayName("isBudgetFeasible_estimatedCostBelowMax_returnsTrue")
    void isBudgetFeasible_estimatedCostBelowMax_returnsTrue() {
        try (MockedStatic<CostEstimator> mockedEstimator = mockStatic(CostEstimator.class)) {
            mockedEstimator.when(() -> CostEstimator.estimateCost(mockTeam, mockProcess, 8))
                    .thenReturn(Float.valueOf(5000f));

            boolean result = budgetService.isBudgetFeasible(mockTeam, mockProcess, 8, 6000f);

            assertTrue(result);
            mockedEstimator.verify(() -> CostEstimator.estimateCost(mockTeam, mockProcess, 8));
        }
    }

    @Test
    @DisplayName("isBudgetFeasible_estimatedCostAboveMax_returnsFalse")
    void isBudgetFeasible_estimatedCostAboveMax_returnsFalse() {
        try (MockedStatic<CostEstimator> mockedEstimator = mockStatic(CostEstimator.class)) {
            mockedEstimator.when(() -> CostEstimator.estimateCost(mockTeam, mockProcess, 8))
                    .thenReturn(Float.valueOf(9000f));

            boolean result = budgetService.isBudgetFeasible(mockTeam, mockProcess, 8, 6000f);

            assertFalse(result);
        }
    }

    @Test
    @DisplayName("isBudgetFeasible_estimatorThrowsException_returnsFalse")
    void isBudgetFeasible_estimatorThrowsException_returnsFalse() throws InvalidHoursPerDayException {
        try (MockedStatic<CostEstimator> mockedEstimator = mockStatic(CostEstimator.class)) {
            mockedEstimator.when(() -> CostEstimator.estimateCost(mockTeam, mockProcess, 25))
                    .thenThrow(new InvalidHoursPerDayException("Too many hours"));

            boolean result = budgetService.isBudgetFeasible(mockTeam, mockProcess, 25, 10000f);

            assertFalse(result);
            mockedEstimator.verify(() -> CostEstimator.estimateCost(mockTeam, mockProcess, 25));
        }
    }
}

// Co-generated with AI assistance (GPT-5), reviewed and validated by Alejandro Ramirez