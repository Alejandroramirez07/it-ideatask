package com.it.company;

import com.it.compAbstract.ClientInteraction;
import com.it.compInterfaces.Discounts;
import com.it.compInterfaces.Evaluation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

public class Project extends ClientInteraction implements Evaluation, Discounts<Float> {
    private static final Logger logger = (Logger) LogManager.getLogger(Project.class);
    private String projectName;
    private String complexityLevel;
    private float initialBudget;

    public Project(String projectName, String complexityLevel, float initialBudget, String clientName) {
        super(clientName);
        this.projectName = projectName;
        this.complexityLevel = complexityLevel;
        this.initialBudget = initialBudget;
    }

    public static boolean isValidProjectName(String projectName) {
        return projectName != null && projectName.length() > 3;
    }

    @Override
    public void evaluateProject(){
        logger.info("This project has a complexity level of " + complexityLevel);
    }

    @Override
    public String achievability() {
        if (Objects.equals(complexityLevel, "low") && initialBudget<1000){
            return projectName + " with complexity " + complexityLevel + " may face issues regarding its low budget compared to its complexity";
        }else if (Objects.equals(complexityLevel, "mid") && initialBudget<2000){
            return projectName + " with complexity " + complexityLevel + " may face issues regarding its low budget compared to its complexity";
        }else if (Objects.equals(complexityLevel, "high") && initialBudget<3000){
            return projectName + " with complexity " + complexityLevel + " may face issues regarding its low budget compared to its complexity";
        }else {
            return projectName + " is achievable";
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getComplexityLevel() {
        return complexityLevel;
    }

    public void setComplexityLevel(String complexityLevel) {
        this.complexityLevel = complexityLevel;
    }

    public float getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(float initialBudget) {
        this.initialBudget = initialBudget;
    }

    @Override
    public void summarize() {
        logger.info("Client details added to the Project");
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", complexityLevel='" + complexityLevel + '\'' +
                ", initialBudget=" + initialBudget +
                ", clientName='" + getClientName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return Float.compare(project.initialBudget, initialBudget) == 0 &&
                Objects.equals(projectName, project.projectName) &&
                Objects.equals(complexityLevel, project.complexityLevel) &&
                Objects.equals(getClientName(), project.getClientName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, complexityLevel, initialBudget, getClientName());
    }

    float discountable;

    @Override
    public void discounts() {
        discountable= (float) (cost*.9);

    }

    float cost;
    @Override
    public Float reOffer(Float cost) {
        if (getInitialBudget() >= cost) {
            logger.info("Your budget covers this project");
            return getInitialBudget();
        } else {
            discounts();
            if (discountable>=cost){
                logger.info("With this discount the client can cover this project. New cost with discount to reoffer: " + discountable);
                return discountable;
            }else{
                logger.info("Even with the discount the budget is not enough");
                return cost;
            }
        }
    }
}
