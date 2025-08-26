package com.solvd.it.company;

import com.solvd.it.compInterfaces.AdsCreation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scope implements AdsCreation {
    private static final Logger LOGGER = LogManager.getLogger(Scope.class);

    private String scope;
    private double budget;
    private String targetAudience;
    private String platform;

    public Scope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public void adsCreation() {
        LOGGER.info("Add this new " + getScope() + " project on social media to create brand placement.");
    }

    @Override
    public void setBudget(double amount) {
        this.budget = amount;
        LOGGER.info("Budget for ads set to $" + amount);
    }

    @Override
    public void getTargetAudience() {
        LOGGER.info("Target audience: " + targetAudience);
    }

    public void setTargetAudience(String audience) {
    }

    @Override
    public void choosePlatform(String platform) {
        this.platform = platform;
        LOGGER.info("Chosen platform for ads: " + platform);
    }

}
