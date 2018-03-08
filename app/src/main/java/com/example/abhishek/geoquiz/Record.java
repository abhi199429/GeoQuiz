package com.example.abhishek.geoquiz;

import java.util.HashSet;

/**
 * Created by Abhishek on 1/16/2018.
 */

public class Record {
    int score = 0;
    HashSet<Integer> set = new HashSet<>();

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HashSet<Integer> getSet() {
        return set;
    }

    public void setSet(HashSet<Integer> set) {
        this.set = set;
    }
}
