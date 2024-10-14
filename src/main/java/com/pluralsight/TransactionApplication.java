package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class TransactionApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();

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
                ledger(); //create ledger
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

    public static void ledger() {

    }

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
}
