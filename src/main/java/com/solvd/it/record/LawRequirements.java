package com.solvd.it.record;

import java.util.List;

public record LawRequirements() {
    public static final int daysUntilApprobation=15;
    public static final List<String> lawsIncluded =List.of
            ("Copyright Law","Patent Law","Digital Millennium Copyright Act (DMCA)");
}


