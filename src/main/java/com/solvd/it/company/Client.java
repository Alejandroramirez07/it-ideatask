package com.solvd.it.company;

import com.solvd.it.annotations.PriorityToRun;
import com.solvd.it.compInterfaces.Discounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


public class Client extends Scope implements Discounts<String> {
    private static final Logger LOGGER = (Logger) LogManager.getLogger(Client.class);
    private String name;
    private String ideaDescription;

    public Client(String name, String ideaDescription, String scope) {
        super(scope);
        this.name = name;
        this.ideaDescription = ideaDescription;
    }

    public static boolean isValidClientName (String name){
        return name != null && name.length()>2;
    }

    public static boolean isValidIdeaDescription (String ideaDescription){
        return ideaDescription != null && ideaDescription.length()>11;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public void setIdeaDescription(String ideaDescription) {
        this.ideaDescription = ideaDescription;
    }

    @Override
    @PriorityToRun
    public void discounts() {
        if (Objects.equals(getScope(), "nat")){
            LOGGER.info("The client " + name + " will get a discount for his/her/its next project.");
        }else{
            LOGGER.info("The client " + name + " could not get a discount this time");
        }
    }
    float cost;
    @Override
    public String reOffer(String name) {
        LOGGER.info("Client´s " + name);
        return name;
    }
}
