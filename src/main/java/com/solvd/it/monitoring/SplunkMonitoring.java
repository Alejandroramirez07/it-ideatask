package com.solvd.it.monitoring;

public class SplunkMonitoring {
    private int projectCode;
    private String monitorComments;
    private int numberIncidents;

    public SplunkMonitoring(int projectCode, String monitorComments, int numberIncidents) {
        this.projectCode=projectCode;
        this.monitorComments=monitorComments;
        this.numberIncidents=numberIncidents;
    }

    public SplunkMonitoring() {

    }

    public int getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(int projectCode) {
        this.projectCode = projectCode;
    }

    public String getMonitorComments() {
        return monitorComments;
    }

    public void setMonitorComments(String monitorComments) {
        this.monitorComments = monitorComments;
    }

    public int getNumberIncidents() {
        return numberIncidents;
    }

    public void setNumberIncidents(int numberIncidents) {
        this.numberIncidents = numberIncidents;
    }

    @Override
    public String toString() {
        return "SplunkMonitoring{" +
                "projectCode=" + projectCode +
                ", monitorComments='" + monitorComments + '\'' +
                ", numberIncidents=" + numberIncidents +
                '}';
    }
}
