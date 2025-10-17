package com.solvd.it.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    // --------------------------------------------------------------------
    // ✅ Static method tests
    // --------------------------------------------------------------------
    @ParameterizedTest(name = "Project name: {0}, expected valid: {1}")
    @CsvSource({
            "'AI', false",
            "'App', false",
            "'AIProject', true",
            "'' , false"
    })
    void isValidProjectName_variousInputs_expectedValidity(String name, boolean expected) {
        String actualName = name.isEmpty() ? null : name;
        boolean result = Project.isValidProjectName(actualName);
        assertEquals(expected, result);
    }

    // --------------------------------------------------------------------
    // ✅ Achievability logic
    // --------------------------------------------------------------------
    @Test
    @DisplayName("achievability_lowBudgetLowComplexity_issueMessage")
    void achievability_lowBudgetLowComplexity_issueMessage() {
        Project p = new Project("Proj1", "low", 500f, "Client");
        String result = p.achievability();
        assertTrue(result.contains("may face issues"));
    }

    @Test
    @DisplayName("achievability_midBudgetMidComplexity_issueMessage")
    void achievability_midBudgetMidComplexity_issueMessage() {
        Project p = new Project("Proj2", "mid", 1500f, "Client");
        String result = p.achievability();
        assertTrue(result.contains("may face issues"));
    }

    @Test
    @DisplayName("achievability_highBudgetHighComplexity_issueMessage")
    void achievability_highBudgetHighComplexity_issueMessage() {
        Project p = new Project("Proj3", "high", 2500f, "Client");
        String result = p.achievability();
        assertTrue(result.contains("may face issues"));
    }

    @Test
    @DisplayName("achievability_sufficientBudget_achievableMessage")
    void achievability_sufficientBudget_achievableMessage() {
        Project p = new Project("Proj4", "high", 4000f, "Client");
        String result = p.achievability();
        assertTrue(result.contains("is achievable"));
    }

    // --------------------------------------------------------------------
    // ✅ Evaluate and summarize (side effects tested indirectly)
    // --------------------------------------------------------------------
    @Test
    @DisplayName("evaluateProject_doesNotThrow")
    void evaluateProject_doesNotThrow() {
        Project p = new Project("ProjEval", "mid", 2000f, "Client");
        assertDoesNotThrow(p::evaluateProject);
    }

    @Test
    @DisplayName("summarize_doesNotThrow")
    void summarize_doesNotThrow() {
        Project p = new Project("ProjSum", "mid", 2000f, "Client");
        assertDoesNotThrow(p::summarize);
    }

    // --------------------------------------------------------------------
    // ✅ Getters and setters
    // --------------------------------------------------------------------
    @Test
    void gettersAndSetters_modifyAndRetrieveValuesCorrectly() {
        Project p = new Project("OldName", "low", 1000f, "Client");
        p.setProjectName("NewProj");
        p.setComplexityLevel("mid");
        p.setInitialBudget(1234f);

        assertEquals("NewProj", p.getProjectName());
        assertEquals("mid", p.getComplexityLevel());
        assertEquals(1234f, p.getInitialBudget());
    }

    // --------------------------------------------------------------------
    // ✅ toString, equals, hashCode
    // --------------------------------------------------------------------
    @Test
    void toString_containsAllFields() {
        Project p = new Project("ToStr", "mid", 2000f, "Client");
        String s = p.toString();
        assertAll(
                () -> assertTrue(s.contains("projectName")),
                () -> assertTrue(s.contains("complexityLevel")),
                () -> assertTrue(s.contains("initialBudget")),
                () -> assertTrue(s.contains("clientName"))
        );
    }

    @Test
    void equalsAndHashCode_consistentForSameValues() {
        Project p1 = new Project("App", "low", 100f, "Client");
        Project p2 = new Project("App", "low", 100f, "Client");
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void equals_differentFields_notEqual() {
        Project p1 = new Project("App1", "low", 100f, "Client");
        Project p2 = new Project("App2", "high", 200f, "Other");
        assertNotEquals(p1, p2);
    }

    // --------------------------------------------------------------------
    // ✅ discounts and reOffer logic
    // --------------------------------------------------------------------
    @Test
    @DisplayName("discounts_appliesTenPercentDiscount")
    void discounts_appliesTenPercentDiscount() {
        Project p = new Project("Disc", "low", 1000f, "Client");
        p.cost = 1000f;
        p.discounts();
        assertEquals(900f, p.discountable, 0.001f);
    }

    @Test
    @DisplayName("reOffer_budgetCoversCost_returnsInitialBudget")
    void reOffer_budgetCoversCost_returnsInitialBudget() {
        Project p = new Project("Offer1", "mid", 2500f, "Client");
        Float result = p.reOffer(2000f);
        assertEquals(2500f, result);
    }

    @Test
    @DisplayName("reOffer_budgetDoesNotCoverButDiscountHelps_returnsDiscounted")
    void reOffer_budgetDoesNotCoverButDiscountHelps_returnsDiscounted() {
        Project p = new Project("Offer2", "low", 1000f, "Client");
        p.cost = 900f; // triggers discount calculation
        Float result = p.reOffer(900f);
        assertEquals(1000f, result);
    }

    @Test
    @DisplayName("reOffer_budgetAndDiscountInsufficient_returnsCost")
    void reOffer_budgetAndDiscountInsufficient_returnsCost() {
        Project p = new Project("Offer3", "mid", 100f, "Client");
        p.cost = 5000f;
        Float result = p.reOffer(5000f);
        assertEquals(5000f, result);
    }
}
