package com.solvd.it.enums;

public enum CloudServices {
    AWS(10),
    GCP(8),
    AZURE(9),
    HUAWEI(6);

    public final int cloudAvailability;


    CloudServices(int cloudAvailability) {
        this.cloudAvailability = cloudAvailability;
    }
}
