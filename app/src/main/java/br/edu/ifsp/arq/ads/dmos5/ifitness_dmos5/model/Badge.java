package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Badge implements Serializable {

    @NonNull
    private String id, name;
    private Atividades type;
    private int level;
    private String badgeImage;
    private Date createdDate;
    private User user;

    public Badge(@NonNull String name, Atividades type, int level, String badgeImage, User user) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.level = level;
        this.badgeImage = badgeImage;
        this.createdDate = new Date();
        this.user = user;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Atividades getType() {
        return type;
    }

    public void setType(Atividades type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBadgeImage() {
        return badgeImage;
    }

    public void setBadgeImage(String badgeImage) {
        this.badgeImage = badgeImage;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
