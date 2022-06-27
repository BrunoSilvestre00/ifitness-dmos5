package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "activity_history")
public class ActivityHistory implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String userID;
    private Atividades type;
    private double distance, duration;
    private String date;

    public ActivityHistory(String userID, Atividades type, double distance, double duration, String date) {
        this.id = UUID.randomUUID().toString();
        this.userID = userID;
        this.type = type;
        this.distance = distance;
        this.duration = duration;
        this.date = date;
    }

    @Ignore
    public ActivityHistory(){
        this("", Atividades.CAMINHADA, 0, 0, "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Atividades getType() {
        return type;
    }

    public void setType(Atividades type) {
        this.type = type;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityHistory activityHistory = (ActivityHistory) o;
        return id.equals(activityHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
