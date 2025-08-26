package com.solvd.it.enums;

public enum Offices {
    RIONEGRO_OFFICE("CC Nicholas office, floor 2"),
    MEDELLIN_OFFICE("Botero park office "),
    ODESSA_OFFICE("City center office"),
    TEXAS_OFFICE("San Antonio office");

    public final String officeAddress;

    Offices(String officeAddress){
        this.officeAddress=officeAddress;
    }
}
