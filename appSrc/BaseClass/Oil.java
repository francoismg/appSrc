package com.archtanlabs.root.essentialoils;

import java.util.ArrayList;

public class Oil {

    private int id;
    private String name;
    private String detail;
    private ArrayList<Symptom> listSymptom;

    public Oil() {

    }

    public Oil(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Oil(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public Oil(int id, String name, ArrayList<Symptom> listSymptom) {
        this.id = id;
        this.name = name;
        this.listSymptom = listSymptom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ArrayList<Symptom> getListSymptom() {
        return listSymptom;
    }

    public void setListSymptom(ArrayList<Symptom> listSymptom) {
        this.listSymptom = listSymptom;
    }
}
