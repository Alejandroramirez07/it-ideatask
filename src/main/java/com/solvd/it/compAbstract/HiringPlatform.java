package com.solvd.it.compAbstract;

import java.util.ArrayList;
import java.util.List;

public abstract class HiringPlatform {
    public abstract void curriculumDelivered();
    public int averageHolidaysReplacement;
    public ArrayList<String> CvsEntered;
    public List<String> toolsManagement;

    private final int placesToFill() {
        return 0;
    }

    public HiringPlatform(int averageHolidays, ArrayList<String> CvsEntered, List <String >toolsManagement){
        this.averageHolidaysReplacement =averageHolidays;
        this.CvsEntered = (CvsEntered != null ? CvsEntered : new ArrayList<>());
    }

    public int getAverageHolidaysReplacement() {
        return averageHolidaysReplacement;
    }

    public void setAverageHolidaysReplacement(int averageHolidaysReplacement) {
        this.averageHolidaysReplacement = averageHolidaysReplacement;
    }
}
