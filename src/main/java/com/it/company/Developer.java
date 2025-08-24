package com.it.company;

import com.it.compAbstract.Employee;
import com.it.compInterfaces.PerformanceTesting;
import java.util.Objects;

import com.it.exceptions.InvalidHolidaysException;
import com.it.exceptions.InvalidHourlyRateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Developer extends Employee implements TimeOutForHire.ITEmployee, PerformanceTesting {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(Developer.class);
    private String name;
    private String level;
    private float hourlyRate;

    public Developer(String name, String level, float hourlyRate, int holidaysScheduled) throws InvalidHourlyRateException, InvalidHolidaysException {
        super(holidaysScheduled);
        if (hourlyRate < 0) {
            throw new InvalidHourlyRateException("Hourly rate cannot be negative: " + hourlyRate);
        }
        if(holidaysScheduled<0){
            throw new InvalidHolidaysException("Holidays cannot be negative " + holidaysScheduled);
        }
        this.name = name;
        this.level = level;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public void itEmployee() {
        LOGGER.info(name + " (Developer) has a " + level + " level");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) throws InvalidHourlyRateException {
        if (hourlyRate < 0) {
            throw new InvalidHourlyRateException("Hourly rate cannot be negative: " + hourlyRate);
        }
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String getRole() {
        return "IT Developer";
    }

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", holidaysScheduled=" + getHolidaysScheduled() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Float.compare(developer.hourlyRate, hourlyRate) == 0 &&
                name.equals(developer.name) &&
                level.equals(developer.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, hourlyRate);
    }

    @Override
    public String performanceTesting() {
        return "The Developer " + name + " wil gest tested according to his/her code quality.";
    }


    @Override
    public void valueRole() {
        if (Objects.equals(getLevel(), "jr") && (getHourlyRate() >= 5 && getHourlyRate() <= 12)) {
               LOGGER.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that has a well estimated revenue for the skills shown ");
        } else if (Objects.equals(getLevel(), "mid") && (getHourlyRate() > 12 && getHourlyRate() <= 25)) {
               LOGGER.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that has a well estimated revenue for the skills shown ");
        } else if (Objects.equals(getLevel(), "sr") && (getHourlyRate() > 25 && getHourlyRate() <= 40)) {
               LOGGER.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that has a well estimated revenue for the skills shown ");
        }else{
            LOGGER.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that is not the right fit for this project ");
        }
    }
}