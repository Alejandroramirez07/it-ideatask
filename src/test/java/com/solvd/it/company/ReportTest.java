package com.solvd.it.company;

import com.solvd.it.annotations.CheckBeforeDelivery;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportTest {

    private Report report;

    @BeforeEach
    void setUp() {
        // Create a report with test values
        report = new Report(10000f, 30, 5, 2);
    }

    @Test
    @DisplayName("printReport()")

    void testPrintReportDoesNotThrow() {
        assertDoesNotThrow(() -> report.printReport());
    }


    @Test
    @DisplayName("CheckBeforeDelivery annotation is present")
    void testAnnotationPresence() {
        assertTrue(report.getClass().isAnnotationPresent(CheckBeforeDelivery.class));
    }

    @Test
    @DisplayName("Getters and setters work correctly")
    void testGettersAndSetters() {
        report.setTotalCost(5000f);
        report.setTotalDays(20);
        report.setTotalPeople(4);

        assertEquals(5000f, report.getTotalCost());
        assertEquals(20, report.getTotalDays());
        assertEquals(4, report.getTotalPeople());
    }

    @Test
    @DisplayName("Equals and hashCode behave consistently")
    void testEqualsAndHashCode() {
        Report same = new Report(10000f, 30, 5, 2);
        Report different = new Report(12000f, 40, 6, 3);

        assertEquals(report, same);
        assertEquals(report.hashCode(), same.hashCode());
        assertNotEquals(report, different);
    }

    @Test
    @DisplayName("toString contains key details")
    void testToStringContainsData() {
        String result = report.toString();
        assertTrue(result.contains("totalCost"));
        assertTrue(result.contains("totalDays"));
    }
}

// Co-generated with AI assistance (GPT-5), reviewed and validated by Alejandro Ramirez
