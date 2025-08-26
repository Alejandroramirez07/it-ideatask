package com.solvd.it.company;

public class StateIncentive <T extends Report> {
    T stateIncentive;
    double totalIncentive;

    public StateIncentive(){
    }

    public void setStateIncentive(T stateIncentive) {
        if (stateIncentive.getTotalCost()>=3000){
            double totalIncentive=stateIncentive.getTotalCost()*0.05;
        }else{
            double totalIncentive=stateIncentive.getTotalCost()*0.01;
        }
    }

    @Override
    public String toString(){
        return "The incentive in this case is " + totalIncentive;
    }
}
