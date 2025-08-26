package com.solvd.it.company;

import com.solvd.it.compInterfaces.StockProjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;


public class TechnologyStack extends Task implements StockProjects {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(TechnologyStack.class);
    private List<String> technologies;

    public TechnologyStack(List<String> technologies, String name, int estimatedHours) {
        super(name, estimatedHours);
        this.technologies = technologies;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    @Override
    public String toString() {
        return "TechnologyStack{" +
                "technologies=" + technologies +
                '}';
    }

    @Override
    public void stockProjects() {
        LOGGER.info("The project with "+ technologies + " will be added to the stack");
    }
}