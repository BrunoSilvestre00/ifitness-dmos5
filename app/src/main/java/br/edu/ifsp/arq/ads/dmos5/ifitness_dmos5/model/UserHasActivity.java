package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import android.util.Log;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserHasActivity implements Serializable, Comparable<UserHasActivity> {

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
        Log.d("BETTER", String.format("%s - %.3f", user.getName(), distTotal));
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

    public Atividades getBetterActivity(){
        int better_pts = 0;
        Atividades better = Atividades.CAMINHADA;
        for(Atividades a : Atividades.values()){
            int pts = (int) this.getDistTotalActivity(a);
            if(pts > better_pts){
                better = a;
                better_pts = pts;
            }
        }
        return better;
    }

    public int getAllPoints(){
        double pts = 0;
        for(ActivityHistory a : activitys){
            pts += a.getDistance();
        }
        return (int) pts;
    }

    public int getBetterPoints(){
        return getAllPoints();
    }

    @Override
    public int compareTo(UserHasActivity obj) {
        int thisPts = this.getBetterPoints();
        int objPts = obj.getBetterPoints();
        return Integer.compare(thisPts, objPts);
    }
}
