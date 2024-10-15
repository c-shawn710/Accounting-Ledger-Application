package com.pluralsight;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        homePage(scanner);
    }

    public static void homePage(Scanner scanner) {
        System.out.println("Welcome to the Home screen! Please select an option: \nD) Add Deposit\nP) Make Payment\nL) View Ledger screen\nX) Exit the application");
        String homeOption;

        while (true) {
            homeOption = scanner.nextLine();
            if (homeOption.equalsIgnoreCase("D")) {
                addDeposit(scanner);
                break;

            } else if (homeOption.equalsIgnoreCase("P")) {
                makePayment(scanner);
                break;

            } else if (homeOption.equalsIgnoreCase("L")) {
                displayLedger(scanner); //create ledger
                break;

            } else if (homeOption.equalsIgnoreCase("X")) {
                System.out.println("Exiting Application");
                System.exit(0);
            } else {
                System.out.println("Please select a valid option: D, P, L, X");
            }
        }
        scanner.close();
    }


    public static void addDeposit(Scanner scanner) {
        System.out.println("Please enter deposit info\nItem Description: ");
        String depositDescriptionInfo = scanner.nextLine();
        System.out.println("Vendor");
        String vendorDepositInfo = scanner.nextLine();
        System.out.println("Amount: ");
        double amountDeposit = scanner.nextDouble();
        scanner.nextLine();

        Info deposit = new Info(depositDescriptionInfo, vendorDepositInfo, amountDeposit);
        saveInfo(deposit);
    }

    public static void makePayment(Scanner scanner) {
        System.out.println("Please enter payment info\nItem Description: ");
        String paymentDescriptionInfo = scanner.nextLine();
        System.out.println("Vendor");
        String vendorPaymentInfo = scanner.nextLine();
        System.out.println("Amount (Input negative): ");
        double amountPayment = scanner.nextDouble();
        scanner.nextLine();

        Info payment = new Info(paymentDescriptionInfo, vendorPaymentInfo, amountPayment);
        saveInfo(payment);
    }

    public static void displayLedger(Scanner scanner) {
        System.out.println("Welcome to the Ledger screen!\nSelect which option you would like to view: \nA) All entries\nD) Deposits\nP) Payments\nR) Reports\nH) Return to Home screen");
        String ledgerOption;

        while (true) {
            ledgerOption = scanner.nextLine();
            if (ledgerOption.equalsIgnoreCase("A")) {
                allEntries();
                break;
            } else if (ledgerOption.equalsIgnoreCase("D")) {
                deposits();
                break;
            } else if (ledgerOption.equalsIgnoreCase("P")) {
                payments();
                break;
            } else if (ledgerOption.equalsIgnoreCase("R")) {
                System.out.println("ily leslie");
                //viewReports();
                break;
            } else if (ledgerOption.equalsIgnoreCase("H")) {
                homePage(scanner);
            } else {
                System.out.println("Please select a valid option: D, P, R, H");

            }
        }
    }       //make more methods and create ledger class


    //Save transactions to the csv file
    public static void saveInfo(Info info) {
        try (FileWriter writer = new FileWriter("transactions.csv", true)) {
            writer.append(info.toCSV()).append("\n"); //.append("\n"); to have it go to next line
            System.out.println("Transaction saved.");
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static void allEntries() {
        try {
            String viewAll = Files.readString(Paths.get("transactions.csv"));
            System.out.println(viewAll);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deposits() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();

            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                if (Double.parseDouble(columns[4]) > 0) {
                    System.out.println(input);
                }
            }
            fileReader.close();
            bufReader.close();
        } catch (Exception e) {
        }
    }
    private static void payments() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();

            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                if (Double.parseDouble(columns[4]) < 0) {
                    System.out.println(input);
                }
            }
            fileReader.close();
            bufReader.close();
        } catch (Exception e) {
        }
    }
}