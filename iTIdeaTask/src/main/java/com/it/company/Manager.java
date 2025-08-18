package com.it.company;

import com.it.compAbstract.Employee;
import com.it.compInterfaces.PerformanceTesting;
import com.it.exceptions.InvalidHolidaysException;
import com.it.exceptions.InvalidHourlyRateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;


public class Manager extends Employee implements TimeOutForHire.ITEmployee, PerformanceTesting {
    private static final Logger logger = (Logger) LogManager.getLogger(Manager.class);
    private String name;
    private float hourlyRate;
    private String level;

    public Manager(String name, float hourlyRate, String level, int holidaysScheduled) throws InvalidHourlyRateException, InvalidHolidaysException {
        super(holidaysScheduled);
        if (hourlyRate < 0) {
            throw new InvalidHourlyRateException("Hourly rate cannot be negative: " + hourlyRate);
        }
        if(holidaysScheduled<0){
            throw new InvalidHolidaysException("Holidays cannot be negative " + holidaysScheduled);
        }
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.level=level;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getLevel (){
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void itEmployee() {
        logger.info(name + " (Manager) oversees the team.");
    }

    @Override
    public String getRole() {
        return "IT Manager";
    }

    @Override
    public void performanceTesting() {
        logger.info("The manager " + name + " wil gest tested according to his/her team management ");
    }

    @Override
    public void valueRole() {
        if (Objects.equals(getLevel(), "jr") && (getHourlyRate() >= 8 && getHourlyRate() <= 16)) {
            logger.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that has a well estimated revenue for the skills shown ");
        } else if (Objects.equals(getLevel(), "mid") && (getHourlyRate() > 16 && getHourlyRate() <= 30)) {
            logger.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that has a well estimated revenue for the skills shown ");
        } else if (Objects.equals(getLevel(), "sr") && (getHourlyRate() > 30 && getHourlyRate() <= 50)) {
            logger.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that has a well estimated revenue for the skills shown ");
        }else {
            logger.info("According to " + getName() + "´s level " + getLevel() + "  and hourly rate " + getHourlyRate() + " the score shows that is not the right fit for this project ");
        }
    }
}

