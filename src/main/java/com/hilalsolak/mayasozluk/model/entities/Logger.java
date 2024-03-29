package com.hilalsolak.mayasozluk.model.entities;

import com.hilalsolak.mayasozluk.model.entities.BaseModel;
import jakarta.persistence.Entity;

@Entity
public class Logger extends BaseModel {
    private String clientId;
    private String activityType;

    public Logger() {
    }

    public Logger(String clientId, String activityType) {
        this.clientId = clientId;
        this.activityType = activityType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
