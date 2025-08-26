package com.solvd.it.company;

import com.solvd.it.compAbstract.Procedures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;


public class Report extends Procedures{
    private static final Logger LOGGER = (Logger) LogManager.getLogger(Report.class);
    private float totalCost;
    private int totalDays;
    private int totalPeople;

    public Report(float totalCost, int totalDays, int totalPeople, int timeOffScheduled) {
        super(timeOffScheduled);
        this.totalCost = totalCost;
        this.totalDays = totalDays;
        this.totalPeople = totalPeople;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public void printReport() {
        LOGGER.info("Project Report:");
        LOGGER.info("Total Cost: $" + totalCost);
        LOGGER.info("Estimated Duration: " + totalDays + " days");
        LOGGER.info("Team Size: " + totalPeople + " people");
        LOGGER.info("The total amount of holidays in this is  " +  timeOffScheduled + " weeks");
    }

    @Override
    public String toString() {
        return "Report{" +
                "totalCost=" + totalCost +
                ", totalDays=" + totalDays +
                ", totalPeople=" + totalPeople +
                ", holidaysScheduled=" + getTimeOffScheduled() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Float.compare(report.totalCost, totalCost) == 0 &&
                totalDays == report.totalDays &&
                totalPeople == report.totalPeople &&
                getTimeOffScheduled() == report.getTimeOffScheduled();
    }

    @Override
    public int hashCode() {

        return Objects.hash(totalCost, totalDays, totalPeople, getTimeOffScheduled());
    }
}