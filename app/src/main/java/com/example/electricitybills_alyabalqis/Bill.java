package com.example.electricitybills_alyabalqis;

public class Bill {
    public String month;
    public int units;
    public double rebate;
    public double totalCharge;
    public double finalCost;

    public Bill() {} // Required for Firebase

    public Bill(String month, int units, double rebate, double totalCharge, double finalCost) {
        this.month = month;
        this.units = units;
        this.rebate = rebate;
        this.totalCharge = totalCharge;
        this.finalCost = finalCost;
    }
}