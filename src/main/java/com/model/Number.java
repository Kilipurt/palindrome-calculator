package com.model;

public class Number {

    private String number;
    private boolean isCalculated;

    public Number(String number, boolean isCalculated) {
        this.number = number;
        this.isCalculated = isCalculated;
    }

    public String getNumber() {
        return number;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }
}
