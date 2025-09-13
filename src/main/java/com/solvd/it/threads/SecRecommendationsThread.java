package com.solvd.it.threads;
import com.solvd.it.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class SecRecommendationsThread extends Thread {
    private static final org.apache.logging.log4j.Logger LOGGER = (Logger) LogManager.getLogger(SecRecommendationsThread.class);

    public ArrayList<String> getRecommendations() {
        ArrayList<String> recommendations = new ArrayList<>();
        recommendations.add("Don´t share your project info online");
        recommendations.add("Avoid public WI-fi networks");
        recommendations.add("Use MFA in your account");
        return recommendations;
    }

    @Override
    public void run(){

        Singleton singletonInstance = Singleton.getInstance();
        singletonInstance.showMessage();

        ArrayList<String> recommendations = getRecommendations();
        for (String recommendation : recommendations){
            LOGGER.info(recommendation);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                LOGGER.error("Thread was interrupted" , e);
            }



        }
    }
}
