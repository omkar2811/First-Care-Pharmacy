package com.example.omkar.navigationdrawer;

public class Medicine {
    private String id;
    private String loc;
    private String med1;
    private String med2;
    private String med3;

    public Medicine()
    {

    }
    public Medicine(String id, String loc, String med1, String med2, String med3){
        //this constructor is required
     this.id = id;
     this.loc = loc;
     this.med1 = med1;
     this.med2 = med2;
     this.med3 = med3;

    }

    public String getid(){
        return id;
    }
    public String getLoc() {
        return loc;
    }

    public String getMed1() {
        return med1;
    }

    public String getMed2() {
        return med2;
    }

    public String getMed3() {
        return med3;
    }
}
