package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ActivityHistory implements Serializable {

    @NonNull
    private String id;
    private Atividades type;
    private User user;
    private double distance, duration;
    private Date date;

    public ActivityHistory(Atividades type, User user, double distance, double duration, Date date) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.user = user;
        this.distance = distance;
        this.duration = duration;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Atividades getType() {
        return type;
    }

    public void setType(Atividades type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
