package com.solvd.it.threads;

import com.solvd.it.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ProjectRecommendationsThread extends Thread{
    private static final org.apache.logging.log4j.Logger LOGGER = (Logger) LogManager.getLogger(ProjectRecommendationsThread.class);

    public ArrayList<String> getProjectRecommendations() {
        ArrayList<String> projectRecommendations =new ArrayList<>();
            projectRecommendations.add(" Declare the scope of your project clearly ");
            projectRecommendations.add(" Don´t over-staff or under-staff your team ");
            projectRecommendations.add(" Use the right mix of datacenters and cloud usage  ");
            return projectRecommendations;

    }

    @Override
    public void run(){

        Singleton singletonInstance = Singleton.getInstance();
        singletonInstance.showMessage();

        ArrayList<String> projRecommendations = getProjectRecommendations();
        for (String recommendations : projRecommendations){
            LOGGER.info(recommendations);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }

    }
}
