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

    public int getLevelWalk(){
        return getLevel(Atividades.CAMINHADA, new int[]{0, 20, 40, 60, 120, 180});
    }

    public int getLevelRun(){
        return getLevel(Atividades.CORRIDA, new int[]{0, 15, 25, 50, 100, 150});
    }

    public int getLevelCyclo(){
        return getLevel(Atividades.CICLISMO, new int[]{0, 40, 80, 120, 200, 280});
    }

    public int getLevelSwim(){
        return getLevel(Atividades.CAMINHADA, new int[]{0, 1, 2, 5, 10, 20});
    }
}
