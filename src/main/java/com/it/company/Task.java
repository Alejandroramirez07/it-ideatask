package com.it.company;

import com.it.compInterfaces.StockProjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task implements StockProjects {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(Task.class);
    private String name;
    private int estimatedHours;

    public Task(String name, int estimatedHours) {
        this.name = name;
        this.estimatedHours = estimatedHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    @Override
    public void stockProjects(){
        LOGGER.info(name + "will be added to the projects stock");
    }
}
