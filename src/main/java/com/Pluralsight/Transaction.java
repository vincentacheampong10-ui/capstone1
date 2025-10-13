package com.Pluralsight;

import java.time.Instant;
import java.time.LocalDate;

public class Transaction {

    private LocalDate dateTime;
    private String vendor;
    private String description;
    private double amount;

    public Transaction(){

    }

    public Transaction(LocalDate dateTime, String vendor, String description, double amount) {
        this.dateTime = dateTime;
        this.vendor = vendor;
        this.description = description;
        this.amount = amount;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "dateTime=" + dateTime +
                ", vendor='" + vendor + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';

    }
}
