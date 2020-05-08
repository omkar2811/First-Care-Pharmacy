package com.example.omkar.navigationdrawer;

import java.util.List;

public class Bookingdetail {
    private List<String> medilist;
    private int total1;

    public Bookingdetail()
    {

    }

    public Bookingdetail(List<String> medilist, int total1) {
        this.medilist = medilist;
        this.total1 = total1;
    }

    public List<String> getMedilist() {
        return medilist;
    }

    public int getTotal1() {
        return total1;
    }
}
