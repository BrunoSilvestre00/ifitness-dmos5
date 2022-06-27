package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "user")
public class User implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String name, email, password;
    private String bornDate;
    private String gender, phone, profileImage;

    private int levelWalk, levelRun, levelCyclo, levelSwim;

    public User(
            String name, String email, String password, String bornDate,
            String gender, String phone, String profileImage,
            int levelWalk, int levelRun, int levelCyclo, int levelSwim) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email =  email;
        this.password = password;
        this.bornDate = bornDate;
        this.gender = gender;
        this.phone = phone;
        this.profileImage = profileImage;
        this.levelWalk = levelWalk;
        this.levelRun = levelRun;
        this.levelCyclo = levelCyclo;
        this.levelSwim = levelSwim;
    }

    @Ignore
    public User(){
        this("", "", "", "", "", "", "", 0, 0, 0, 0);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevelWalk() {
        return levelWalk;
    }

    public void setLevelWalk(int levelWalk) {
        this.levelWalk = levelWalk;
    }

    public int getLevelRun() {
        return levelRun;
    }

    public void setLevelRun(int levelRun) {
        this.levelRun = levelRun;
    }

    public int getLevelCyclo() {
        return levelCyclo;
    }

    public void setLevelCyclo(int levelCyclo) {
        this.levelCyclo = levelCyclo;
    }

    public int getLevelSwim() {
        return levelSwim;
    }

    public void setLevelSwim(int levelSwim) {
        this.levelSwim = levelSwim;
    }

    public int getPointsWalk(){
        return 0;
    }

    public int getPointsRun(){
        return 0;
    }

    public int getPointsCyclo(){
        return 0;
    }

    public int getPointsSwim(){
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User usuario = (User) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
