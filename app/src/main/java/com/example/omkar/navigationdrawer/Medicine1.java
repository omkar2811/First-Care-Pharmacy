package com.example.omkar.navigationdrawer;

import java.util.List;

public class Medicine1 {
    private List<String> medicine;
    private String stname;
    private String ownname;
    private String stloc;
    private String stno;
    private String stpasss;
    private String stadd;


    public Medicine1()
    {

    }

    public List<String> getMedicine() {
        return medicine;
    }

    public String getStname() {
        return stname;
    }

    public String getOwnname() {
        return ownname;
    }

    public String getStloc() {
        return stloc;
    }

    public String getStno() {
        return stno;
    }

    public String getStpasss() {
        return stpasss;
    }

    public String getStadd() {
        return stadd;
    }

    public Medicine1(List<String> medicine, String stadd, String stname, String ownname, String stloc, String stno, String stpasss) {

        this.medicine = medicine;
        this.stadd = stadd;
        this.stname = stname;
        this.ownname = ownname;
        this.stloc = stloc;
        this.stno = stno;
        this.stpasss = stpasss;
    }
}
