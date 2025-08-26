package com.solvd.it.company;

import java.util.ArrayList;
import java.util.Arrays;

public final class LawRequirements {
    private static final int daysUntilApprobation=15;
    private static final ArrayList<String> lawsIncluded = new ArrayList<>(
            Arrays.asList("Copyright Law" , "Patent Law", "Digital Millennium Copyright Act (DMCA)"));

    public static int getDaysUntilApprobation() {
        return daysUntilApprobation;
    }

    public static ArrayList<String> lawsIncluded(){
        return lawsIncluded;
    }
}
