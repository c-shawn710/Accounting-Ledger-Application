package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Info {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public Info(String description, String vendor, double amount) {
        //Automatically set the date & time when transaction is created
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //Create method that formats the data in the csv file
    public String toCSV() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

   /* //Had to look this up
    public boolean currentMonth() {
        LocalDate transactionDate = LocalDate.parse(this.date);
        LocalDate currentDate = LocalDate.now();
        return transactionDate.getMonthValue() == currentDate.getMonthValue() && transactionDate.getYear() == currentDate.getYear();
    }*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}


