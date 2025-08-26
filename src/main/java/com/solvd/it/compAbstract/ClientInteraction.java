package com.solvd.it.compAbstract;

public abstract class ClientInteraction {
    protected String clientName;

    public ClientInteraction(String clientName){
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public abstract void summarize();
}

