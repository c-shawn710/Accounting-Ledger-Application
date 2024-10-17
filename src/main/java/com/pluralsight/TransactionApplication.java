package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
            if (homeOption.equals("D")) {
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
                break;
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


        Info deposit = new Info(depositDescriptionInfo, vendorDepositInfo, amountDeposit);
        saveInfo(deposit);
        Scanner homeScanner = new Scanner(System.in);
        homePage(homeScanner);
    }

    public static void makePayment(Scanner scanner) {
        System.out.println("Please enter payment info\nItem Description: ");
        String paymentDescriptionInfo = scanner.nextLine();
        System.out.println("Vendor");
        String vendorPaymentInfo = scanner.nextLine();
        System.out.println("Amount (Input negative): ");
        double amountPayment = scanner.nextDouble();

        Info payment = new Info(paymentDescriptionInfo, vendorPaymentInfo, amountPayment);
        saveInfo(payment);

        Scanner homeScanner = new Scanner(System.in);
        homePage(homeScanner);
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
                reports(scanner);
                break;
            } else if (ledgerOption.equalsIgnoreCase("H")) {
                homePage(scanner);
            } else {
                System.out.println("Please select a valid option: D, P, R, H");
            }
        }
        //Received help to loop back to view options instead of exiting app
        System.out.println("\nPress Enter to go back");
        scanner.nextLine();
        Scanner ledgerScanner = new Scanner(System.in);
        displayLedger(ledgerScanner);
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
    //Received help on displaying transactions in descending order
    public static void allEntries() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            ArrayList<String> columns = new ArrayList<String>();
            while((input = bufReader.readLine()) != null) {
                columns.add(input);
            }
            System.out.println("\nDate|Time|Description|Vendor|Amount");
            for (int i = 0; i < columns.size(); i++) {
                columns.sort(Collections.reverseOrder());
                System.out.println(columns.get(i));
            }
            fileReader.close();
            bufReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deposits() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            ArrayList<String> arrayColumns = new ArrayList<String>();
            String input;
            bufReader.readLine();

            System.out.println("\nDate|Time|Description|Vendor|Amount");
            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                if (Double.parseDouble(columns[4]) > 0) {
                    arrayColumns.add(input);
                }
            }
            for (int i = 0; i < arrayColumns.size(); i++) {
                arrayColumns.sort(Collections.reverseOrder());
                System.out.println(arrayColumns.get(i));
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
            ArrayList<String> arrayColumns = new ArrayList<String>();
            String input;
            bufReader.readLine();

            System.out.println("\nDate|Time|Description|Vendor|Amount");
            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                if (Double.parseDouble(columns[4]) < 0) {
                    arrayColumns.add(input);
                }
            }
                for (int i = 0; i < arrayColumns.size(); i++) {
                    arrayColumns.sort(Collections.reverseOrder());
                    System.out.println(arrayColumns.get(i));
                }
            fileReader.close();
            bufReader.close();
        }   catch (Exception e) {
        }
    }

    private static void reports(Scanner scanner) {
        System.out.println("\nWelcome to the Reports screen!\nSelect which option you would like to view: \n1) Month To Date\n2) Previous Month\n3) Year To Date\n4) Previous Year\n5) Search by Vendor\n0) Back to Ledger screen");
        int reportsOption;

        while (true) {
            reportsOption = scanner.nextInt();
            scanner.nextLine();
            if (reportsOption == 1) {
                monthToDate();
            } else if (reportsOption == 2) {
                previousMonth();
            } else if (reportsOption == 3) {
                yearToDate();
            } else if (reportsOption == 4) {
                previousYear();
            } else if (reportsOption == 5) {
                searchVendor(scanner);
            } else if (reportsOption == 0) {
                displayLedger(scanner);
            } else {
                System.out.println("Please select a valid option: 1, 2, 3, 4, 5, 0");
            }
            System.out.println("\nPress Enter to go back");
            scanner.nextLine();
            Scanner reportScanner = new Scanner(System.in);
            reports(reportScanner);
            scanner.close();
        }
    }

    private static void monthToDate() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            LocalDate currentDate = LocalDate.now();
            ArrayList<String> columns = new ArrayList<String>();
            while ((input = bufReader.readLine()) != null) {
                columns.add(input);
            }
            System.out.println("\nDate|Time|Description|Vendor|Amount");
            columns.sort(Collections.reverseOrder());
            for(int i = 0; i < columns.size(); i++) {
                if (LocalDate.parse((columns.get(i).substring(0, 10))).getMonthValue() == currentDate.getMonthValue() && LocalDate.parse((columns.get(i).substring(0, 10))).getYear() == currentDate.getYear()) {
                    System.out.println(columns.get(i));
                }
            }
            fileReader.close();
            bufReader.close();
        } catch (Exception e) {
        }
    }

    private static void previousMonth() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            LocalDate currentDate = LocalDate.now();
            ArrayList<String> columns = new ArrayList<String>();
            while ((input = bufReader.readLine()) != null) {
                columns.add(input);
            }
            System.out.println("\nDate|Time|Description|Vendor|Amount");
            columns.sort(Collections.reverseOrder());
            for(int i = 0; i < columns.size(); i++) {
                if (LocalDate.parse((columns.get(i).substring(0, 10))).getMonthValue() == currentDate.getMonthValue() -1 && LocalDate.parse((columns.get(i).substring(0, 10))).getYear() == currentDate.getYear()) {
                    System.out.println(columns.get(i));
                }
            }
            fileReader.close();
            bufReader.close();
        } catch (Exception e) {
        }
    }

    private static void yearToDate() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            LocalDate currentDate = LocalDate.now();
            ArrayList<String> columns = new ArrayList<String>();
            while ((input = bufReader.readLine()) != null) {
                columns.add(input);
            }
            System.out.println("\nDate|Time|Description|Vendor|Amount");
            columns.sort(Collections.reverseOrder());
            for(int i = 0; i < columns.size(); i++) {
                if (LocalDate.parse((columns.get(i).substring(0, 10))).getYear() == currentDate.getYear()) {
                    System.out.println(columns.get(i));
                }
            }
            fileReader.close();
            bufReader.close();
            } catch (Exception e) {
            }
        }
        private static void previousYear () {
            try {
                FileReader fileReader = new FileReader("transactions.csv");
                BufferedReader bufReader = new BufferedReader(fileReader);
                String input;
                bufReader.readLine();
                LocalDate currentDate = LocalDate.now();
                ArrayList<String> columns = new ArrayList<String>();
                while ((input = bufReader.readLine()) != null) {
                    columns.add(input);
                }
                System.out.println("\nDate|Time|Description|Vendor|Amount");
                columns.sort(Collections.reverseOrder());
                for (int i = 0; i < columns.size(); i++) {
                    if (LocalDate.parse((columns.get(i).substring(0, 10))).getYear() == currentDate.getYear() -1 ) {
                        System.out.println(columns.get(i));
                    }
                }
                fileReader.close();
                bufReader.close();
            } catch (Exception e) {
            }
        }
        private static void searchVendor (Scanner scanner) {
            try {
                FileReader fileReader = new FileReader("transactions.csv");
                BufferedReader bufReader = new BufferedReader(fileReader);
                ArrayList<String> arrayColumns = new ArrayList<String>();
                String input;
                bufReader.readLine();
                System.out.println("Enter Vendor name: ");
                String inputVendor = scanner.nextLine();
                System.out.println("Date|Time|Description|Vendor|Amount\n");
                while ((input = bufReader.readLine()) != null) {
                    String[] columns = input.split("\\|");
                    String vendorName = columns[3];
                    if(vendorName.equalsIgnoreCase(inputVendor)) {
                        arrayColumns.add(input);
                    }
                }
                for(int i = 0; i < arrayColumns.size(); i++){
                    arrayColumns.sort(Collections.reverseOrder());
                    System.out.println(arrayColumns.get(i));
                }
                fileReader.close();
                bufReader.close();
            } catch (Exception e) {
            }
        }
    }
