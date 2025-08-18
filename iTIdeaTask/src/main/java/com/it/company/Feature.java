package com.it.company;

import java.util.List;
import java.util.Objects;

public class Feature {
    private String featureName;
    private int estimatedDays;
    private List<String> featuresList;

    public Feature(String featureName, int estimatedDays, List<String> featuresList) {
        this.featureName = featureName;
        this.estimatedDays = estimatedDays;
        this.featuresList = featuresList;
    }

    public Feature(int estimatedDays, List<String> featuresList) {
        this.estimatedDays = estimatedDays;
        this.featuresList = featuresList;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public int getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(int estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public List<String> getFeaturesList() {
        return featuresList;
    }

    public void setFeaturesList(List<String> featuresList) {
        this.featuresList = featuresList;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "featureName='" + featureName + '\'' +
                ", estimatedDays=" + estimatedDays +
                ", featuresList=" + featuresList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;
        Feature feature = (Feature) o;
        return estimatedDays == feature.estimatedDays &&
                Objects.equals(featureName, feature.featureName) &&
                Objects.equals(featuresList, feature.featuresList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureName, estimatedDays, featuresList);
    }
}

