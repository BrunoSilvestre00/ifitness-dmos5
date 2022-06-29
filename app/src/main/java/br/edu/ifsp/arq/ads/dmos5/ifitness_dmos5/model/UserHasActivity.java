package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class UserHasActivity {

    @Embedded
    private User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userID"
    )

    private List<ActivityHistory> activitys;

    public UserHasActivity(){
        activitys = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ActivityHistory> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<ActivityHistory> activitys) {
        this.activitys = activitys;
    }
}
