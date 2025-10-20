package com.solvd.it.company;

import com.solvd.it.app.Main;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

    @Test
    void main_runsSuccessfully_andLogsMessages() {
        // Build the simulated input in the exact order Main expects.
        // Every entry is followed by a newline. The final newline simulates pressing Enter one last time.
        List<String> inputs = List.of(
                "ClientX",                                 // clientName
                "This is a great app idea",                // ideaDescription
                "nat",                                     // scope
                "ProjectX",                                // projectName
                "mid",                                     // complexity
                "3",                                       // features (number)
                "5000.0",                                  // budget (float)
                "12/31/2025",                              // deadlineDelivery
                "2",                                       // numDevs
                // Dev #1
                "John",
                "jr",
                "30.0",                                    // hourly rate (float)
                "80",                                      // score
                "2",                                       // holidaysSchedule
                // Dev #2
                "Jane",
                "sr",
                "60.0",
                "90",
                "3",
                // project duration and hours per week
                "12",                                      // weeks
                "8",                                       // hoursPerWeek (use a value accepted by CostEstimator)
                // technologies
                "Java,Spring",
                // features names (3 features)
                "Feature1",
                "Feature2",
                "Feature3",
                // Manager info
                "ManagerName",
                "50.0",                                    // managerRate
                "mid",                                     // managerLevel
                "2",                                       // managerHolidays
                // Architecture option (1-5)
                "1",
                // Project monitoring and scope inputs
                "101",                                     // projectCode
                "Some comments",                           // monitorComments
                "0",                                       // numberIncidents
                "0",                                       // optionDeletingMonitoring (0 = don't delete)
                // project scope and platform
                "E-commerce",                              // scopeName
                "LinkedIn"                                 // platform
                // <-- Add more final entries here if your Main expects more inputs
        );

        // join with system line separator and add final trailing newline
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        for (String s : inputs) {
            sb.append(s).append(sep);
        }
        sb.append(sep);

        String simulatedInput = sb.toString();

        InputStream originalIn = System.in;
        try (ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8))) {
            System.setIn(in);

            assertDoesNotThrow(() -> Main.main(new String[]{}));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setIn(originalIn);
        }
    }
}
