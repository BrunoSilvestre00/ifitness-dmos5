package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserHasActivity implements Serializable {

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

    public double getDistTotalActivity(Atividades a){
        double distTotal = 0;
        for(ActivityHistory ah : activitys){
            if(ah.getType() == a){
                distTotal += ah.getDistance();
            }
        }
        return distTotal;
    }

    public double getTimeTotalActivity(Atividades a){
        double timeTotal = 0;
        for(ActivityHistory ah : activitys){
            if(ah.getType() == a){
                timeTotal += ah.getDuration();
            }
        }
        return timeTotal;
    }

    private int getLevel(Atividades a, int[] metrics){
        double distTotal = getDistTotalActivity(a);
        for(int i = 5; i >= 0; i--){
            if(distTotal >= metrics[i])
                return i;
        }
        return 0;
    }

    public int getLevelActivity(Atividades activity){
        switch (activity){
            case CAMINHADA:
                return getLevel(Atividades.CAMINHADA, new int[]{0, 20, 40, 60, 120, 180});
            case CORRIDA:
                return getLevel(Atividades.CORRIDA, new int[]{0, 15, 25, 50, 100, 150});
            case CICLISMO:
                return getLevel(Atividades.CICLISMO, new int[]{0, 40, 80, 120, 200, 280});
            default:
                return getLevel(Atividades.NATACAO, new int[]{0, 1, 2, 5, 10, 20});
        }
    }
}
