package com.solvd.it.enums;

public enum ArchitectureTypes {
    HEXAGONAL(10),
    MICROSERVICES(9),
    EVENT_DRIVEN(8),
    SERVICE_ORIENTED(7),
    MONOLITHIC(5);

    final public int maintenanceLevelArchitecture;

    ArchitectureTypes(int maintenanceLevelArchitecture){
        this.maintenanceLevelArchitecture=maintenanceLevelArchitecture;
    }
}
