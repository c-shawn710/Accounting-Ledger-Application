package com.pluralsight;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
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
        //Help from friend to loop back to view options instead of app exiting
        System.out.println("Press Enter to go back");
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

    private static void reports(Scanner scanner) {
        System.out.println("Welcome to the Reports screen!\nSelect which option you would like to view: \n1) Month To Date\n2) Previous Month\n3) Year To Date\n4) Previous Year\n5) Search by Vendor\n0) Back to Ledger screen");
        int reportsOption;

        while (true) {
            reportsOption = scanner.nextInt();
            scanner.nextLine();
            if (reportsOption == 1) {
                monthToDate();
                break;
            } else if (reportsOption == 2) {
                previousMonth();
                break;
            } else if (reportsOption == 3) {
                yearToDate();
                break;
            } else if (reportsOption == 4) {
                previousYear();
                break;
            } else if (reportsOption == 5) {
                searchVendor(scanner);
                break;
            } else if (reportsOption == 0) {
                displayLedger(scanner);
                break;
            } else {
                System.out.println("Please select a valid option: 1, 2, 3, 4, 5, 0");
            }
        }
    }

    private static void monthToDate() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            LocalDate inputDate;
            LocalDate currentDate = LocalDate.now();
            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                inputDate = LocalDate.parse(columns[0]);
                if (inputDate.getMonthValue() == currentDate.getMonthValue() && inputDate.getYear() == currentDate.getYear()) {
                    System.out.println(input);
                }
            }
        } catch (Exception e) {
        }
    }

    private static void previousMonth() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            LocalDate inputDate;
            LocalDate currentDate = LocalDate.now();
            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                inputDate = LocalDate.parse(columns[0]);
                if (inputDate.getMonthValue() == currentDate.getMonthValue() - 1 && inputDate.getYear() == currentDate.getYear()) {
                    System.out.println(input);
                }
            }
        } catch (Exception e) {
        }
    }

    private static void yearToDate() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;
            bufReader.readLine();
            LocalDate inputDate;
            LocalDate currentDate = LocalDate.now();
            while ((input = bufReader.readLine()) != null) {
                String[] columns = input.split("\\|");
                inputDate = LocalDate.parse(columns[0]);
                if (inputDate.getYear() == currentDate.getYear()) {
                    System.out.println(input);
                }
            }
        } catch (Exception e) {
        }
    }
        private static void previousYear () {
            try {
                FileReader fileReader = new FileReader("transactions.csv");
                BufferedReader bufReader = new BufferedReader(fileReader);
                String input;
                bufReader.readLine();
                LocalDate inputDate;
                LocalDate currentDate = LocalDate.now();
                while ((input = bufReader.readLine()) != null) {
                    String[] columns = input.split("\\|");
                    inputDate = LocalDate.parse(columns[0]);
                    if (inputDate.getMonthValue() == currentDate.getMonthValue() && inputDate.getYear() == currentDate.getYear() - 1) {
                        System.out.println(input);
                    }
                }
            } catch (Exception e) {
            }
        }

        private static void searchVendor (Scanner scanner) {
            try {
                FileReader fileReader = new FileReader("transactions.csv");
                BufferedReader bufReader = new BufferedReader(fileReader);
                String input;
                bufReader.readLine();
                System.out.println("Enter Vendor name: ");
                String inputVendor = scanner.nextLine();
                while ((input = bufReader.readLine()) != null) {
                    String[] columns = input.split("\\|");
                    String vendorName = columns[3];
                    if(vendorName.equalsIgnoreCase(inputVendor)) {
                        System.out.println(input);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
