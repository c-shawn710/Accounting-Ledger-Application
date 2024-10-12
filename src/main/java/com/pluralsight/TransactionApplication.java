package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TransactionApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


            System.out.println("Welcome to the Home screen! Please select an option: \nD) Add Deposit\nP) Make Payment\nL) View Ledger screen\nX) Exit the application");
            String homeOption;
            bufferedReader.readLine();

            while (true) {
                homeOption = scanner.nextLine();
                if (homeOption.equalsIgnoreCase("D")) {
                    System.out.println("Please enter deposit info\nItem Description: ");
                    String depositDescriptionInfo = scanner.nextLine();
                    System.out.println("Vendor");
                    String vendorDepositInfo = scanner.nextLine();
                    System.out.println("Amount: ");
                    double amountDeposit = scanner.nextDouble();
                    scanner.nextLine();
                    break;

                } else if (homeOption.equalsIgnoreCase("P")) {
                    System.out.println("Please enter payment info\nItem Description: ");
                    String depositPaymentInfo = scanner.nextLine();
                    System.out.println("Vendor");
                    String vendorPaymentInfo = scanner.nextLine();
                    System.out.println("Amount: ");
                    double amountPayment = scanner.nextDouble();
                    scanner.nextLine();
                    break;

                } else if (homeOption.equalsIgnoreCase("L")) {
                    ledger();
                    break;

                } else if (homeOption.equalsIgnoreCase("X")) {
                    System.out.println("Exiting Application");
                    System.exit(0);
                } else {
                    System.out.println("Please select a valid option: D, P, L, X");
                }
            }



            /*while ((homeOption = bufferedReader.readLine() != null)) {
                String[] columns = homeOption.split("\\|");
            *//*String input;
            while ((input = bufferedReader.readLine()) != null) {
                String[] info = input.split("\\|");
                String description = info[2];*/


        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static void ledger() {

    }
}

