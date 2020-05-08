package com.example.omkar.navigationdrawer;

public class MedicalList {

    public String medicine,id;

    public MedicalList()
    {

    }
    public MedicalList(String medicine,String id) {
        this.medicine = medicine;
        this.id =  id;

    }

    public String getMedicine() {
        return medicine;
    }

    public String getId() {
        return id;
    }
}
