package com.example.jesse.barscan;

/**
 * Created by jesse on 12/14/2016.
 */

public class DBObject {

    int dobVar;
    int zipVar;
    String dateVar;
    String genderVar;

    public DBObject(){

    }

    public DBObject(String dateVar, int dobVar, int zipVar, String genderVar){
        this.dateVar = dateVar;
        this.dobVar = dobVar;
        this.zipVar = zipVar;
        this.genderVar = genderVar;
    }

    public String getGenderVar() {
        return genderVar;
    }

    public void setDobVar(int dobVar) {
        this.dobVar = dobVar;
    }

    public void setZipVar(int zipVar) {
        this.zipVar = zipVar;
    }

    public void setDateVar(String dateVar) {
        this.dateVar = dateVar;
    }

    public void setGenderVar(String genderVar) {
        this.genderVar = genderVar;
    }

    public String getDateVar() {
        return dateVar;
    }

    public int getZipVar() {
        return zipVar;
    }

    public int getDobVar() {
        return dobVar;
    }
}
