package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidHoursPerDayException;

public class CostEstimator {

    public static float estimateCost (Team team, ProjectProcess projectProcess, int hoursPerDay) throws InvalidHoursPerDayException {
        if(hoursPerDay>24 || hoursPerDay<0){
            throw new InvalidHoursPerDayException("This amount of work hours is invalid :" + hoursPerDay);
        }
        float rate = team.getAverageHourlyRate();
        int totalHours = team.getTeamSize() * hoursPerDay * projectProcess.getHoursPerWeek();
        return rate * totalHours;
    }
}
