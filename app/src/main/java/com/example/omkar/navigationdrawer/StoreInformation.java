package com.example.omkar.navigationdrawer;

import java.util.List;

public class StoreInformation {
   public String ownname,stname,stloc,stno,stpass,id,stadd;
    public List<String> medicine;

    public StoreInformation()
    {

    }


    public StoreInformation(List<String> medicine,String stadd,String ownname, String stname, String stloc, String stno, String stpass){
        this.medicine = medicine;
        this.stadd = stadd;
        this.ownname = ownname;
        this.stname = stname;
        this.stloc = stloc;
        this.stno = stno;
        this.stpass = stpass;
    }

}
