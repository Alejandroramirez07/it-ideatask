package com.solvd.it.company;
import com.solvd.it.compAbstract.TimeMetrics;
import com.solvd.it.compInterfaces.Evaluation;
import com.solvd.it.exceptions.InvalidProjectDeadlineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ProjectProcess extends TimeMetrics implements Evaluation {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(ProjectProcess.class);
    private int weeks;
    private int hoursPerWeek;

    public ProjectProcess(int weeks, int hoursPerWeek, String deadlineDelivery) throws InvalidProjectDeadlineException {
        super(deadlineDelivery);
        if (deadlineDelivery == null || deadlineDelivery.isBlank()) {
            throw new InvalidProjectDeadlineException("Deadline cannot be empty");
        }
        if (!deadlineDelivery.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new InvalidProjectDeadlineException("Deadline must be in format MM/DD/YYYY: " + deadlineDelivery);
        }
        this.weeks = weeks;
        this.hoursPerWeek = hoursPerWeek;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getHoursPerWeek(){
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "weeks=" + weeks +
                ", hoursPerWeek=" + hoursPerWeek +
                ", deadlineDelivery='" + getDeadlineDelivery() + '\'' +
                '}';
    }

    LocalDate todaysDate;
    {
        todaysDate = LocalDate.now();
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate deadlineDate=LocalDate.parse(getDeadlineDelivery(), formatter);

    @Override
    public void evaluateProject() {
        LOGGER.info("Today is " + todaysDate + " This project must be delivered before " + getDeadlineDelivery());
    }

    @Override
    public Object achievability() {

        if (deadlineDate.isBefore(todaysDate) || deadlineDate.isEqual(todaysDate)) {
            LOGGER.warn("Deadline has passed or is today!");
            return false;
        } else {
            LOGGER.info("Deadline is still in the future.");
            return true;
        }
    }
}