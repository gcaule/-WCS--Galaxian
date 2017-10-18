package com.wcs.gcaule.galaxian;

/**
 * Created by apprenti on 18/10/17.
 */

public class Player {

    String name;
    int score;
    String password;

    public Player() {

    }

    public Player(String name, int score, String password) {
        this.name = name;
        this.score = score;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}