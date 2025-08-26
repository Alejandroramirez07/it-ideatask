package com.solvd.it.company;

import com.solvd.it.exceptions.InvalidTeamSizeException;

import java.util.List;

public class Team {
    private List<Developer> developers;
    public static final int maxTeam= 10;

    public Team(List<Developer> developers, int maxTeam) throws InvalidTeamSizeException {
        if (developers.size() > maxTeam) {
            throw new InvalidTeamSizeException("Team size exceeds the maximum allowed: " + maxTeam);
        }
        if (developers.size() < 1 || developers.isEmpty()) {
            throw new InvalidTeamSizeException("A team must have at least one developer.");
        }
        this.developers = developers;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public int getTeamSize() {
        return developers.size();
    }

    public int getMaxTeam(){
        return maxTeam;
    }

    public float getAverageHourlyRate() {
        float total = 0;
        for (Developer dev : developers) {
            total += dev.getHourlyRate();
        }
        return total / developers.size();
    }
}

