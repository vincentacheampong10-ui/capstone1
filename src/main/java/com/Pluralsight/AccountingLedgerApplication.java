package com.Pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AccountingLedgerApplication {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean homeScreen = true;

        while (homeScreen) {
            System.out.println("\n===== HOME SCREEN =====");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice:");

            String choice1 = scanner.nextLine().trim().toUpperCase();

            runHomeScreen(choice1);

        }
    }

    private static void runHomeScreen(String choice1) {
        switch (choice1) {
            ///  In case this option is choosen, do this.
            case "D":
                makeDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                runLedgerMenu(scanner);
                break;
            case "X":
                break;

            default:/// Catch all
                System.out.println("Choose an option below.");
        }
    }

    private static void makeDeposit() {
        System.out.print("Enter deposit date (YYYY-MM-DD):");
        String banksName = scanner.nextLine();

        System.out.print("Enter account holder name:");
        String accountName = scanner.nextLine();

        System.out.print("Enter deposit ID:");
        String depositId = scanner.nextLine();

        System.out.print("Enter deposit amount:");
        double depositAmount = scanner.nextInt();

        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true); // 'true' for append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Save the data in CSV format: BanksName|accountName|depositID|depositAmount
            printWriter.println(banksName + "|" + accountName + "|" + depositId + "|" + depositAmount);

            printWriter.close();
            System.out.println("Deposit saved successfully!");


        } catch (IOException e) {
            e.printStackTrace();// display stack trace if there was an error
        }
    }

    private static void makePayment() {
        System.out.print("Enter payment date (YYYY-MM-DD):");
        String paymentDate = scanner.nextLine();

        System.out.print("Enter account name:");
        String accountNameForDebit = scanner.nextLine();

        System.out.print("Enter Payment ID:");
        String paymentId = scanner.nextLine();

        System.out.print("Enter Payment amount:");
        int paymentAmount = scanner.nextInt();

        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true); // 'true' for append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Save the data in CSV format: paymentDate|accountNameForPayment|paymentID|paymentAmount
            printWriter.println(paymentDate + "|" + accountNameForDebit + "|" + paymentId + "|" + paymentAmount);

            printWriter.close();
            System.out.println("Payment made successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runLedgerMenu(Scanner scanner) {
        boolean ledger = true;

        while (ledger) {
            System.out.println("\n===== LEDGER MENU =====");
            System.out.println("Choose an option:");
            System.out.println("A) All - Display all entries");
            System.out.println("D) Deposits - Only deposits");
            System.out.println("P) Payments - Only payments");
            System.out.println("R) Reports- Custom Search");
            System.out.println("X) Exit");
            System.out.print("Enter Choice:");

            String choice2 = scanner.nextLine().trim().toLowerCase();

            switch (choice2) {
                ///  In case this option is choosen, do this.
                case "a":
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
                        String line;
                        reader.readLine();

                        System.out.println("===== ALL TRANSACTIONS =====");
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }

                        reader.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "d":
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
                        String line = reader.readLine();

                        if (line == null) {
                            // File is empty
                            System.out.println("There is no deposit available.");
                            ledger = false; // exit ledger
                        } else {

                            System.out.println(line);

                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }

                            ledger = false; // exit after displaying
                        }

                        reader.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "p":
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
                        String line = "";
                        reader.readLine();

                        if (line == null) {
                            System.out.println("There is no payment available.");
                            ledger = false; // exit ledger
                        } else {

                            System.out.println(line);

                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split("\\|");
                                if (parts.length >= 4) {
                                    // Get the amount part and remove spaces
                                    String priceAmount = parts[4].trim();
                                    double amount = Double.parseDouble(priceAmount);
                                    if (amount < 0) {
                                        System.out.println(priceAmount);
                                    }
                                }
                            }

                            ledger = false; // exit after displaying
                        }

                        reader.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "r":
                    runReportsMenu(scanner);
                    break;
                case "x":
                    break;
                default:
                    System.out.println("Choose an option below.");
            }
        }
    }

    public static void runReportsMenu(Scanner scanner) {

        ArrayList<Transaction> list = new ArrayList<>();

        boolean reports = true;

        while (reports) {
            System.out.println("\n===== REPORTS MENU =====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom search");
            System.out.println("0) Exit");
            System.out.print("Enter Choice:");
            String choice3 = scanner.nextLine().trim();
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

            switch (choice3) {
                case "1": // Month To Date
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        String line;
                        bufferedReader.readLine(); // skip header
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateformatter);
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
                    LocalDateTime now = LocalDateTime.now();

                    System.out.println("===== MONTH TO DATE TRANSACTIONS =====");
                    for (Transaction transaction1 : list) {
                        if (!transaction1.getDateTime().isBefore(startOfMonth) && !transaction1.getDateTime().isAfter(now)) {
                            System.out.println(transaction1);
                        }
                    }
                    break;

                case "2": // Previous Month
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        String line;
                        bufferedReader.readLine(); // skip header
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateformatter);
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    LocalDate firstDayCurrentMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDateTime startOfPreviousMonth = firstDayCurrentMonth.minusMonths(1).atStartOfDay();
                    LocalDateTime endOfPreviousMonth = firstDayCurrentMonth.minusDays(1).atTime(23, 59, 59);

                    System.out.println("===== PREVIOUS MONTH TRANSACTIONS =====");
                    for (Transaction transaction1 : list) {
                        if (!transaction1.getDateTime().isBefore(startOfPreviousMonth) &&
                                !transaction1.getDateTime().isAfter(endOfPreviousMonth)) {
                            System.out.println(transaction1);
                        }
                    }
                    break;

                case "3": // Year To Date
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        String line;
                        bufferedReader.readLine();
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateformatter);
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    LocalDateTime startOfYear = LocalDate.now().withDayOfYear(1).atStartOfDay();
                    now = LocalDateTime.now();

                    System.out.println("===== YEAR TO DATE TRANSACTIONS =====");
                    for (Transaction transaction1 : list) {
                        if (!transaction1.getDateTime().isBefore(startOfYear) && !transaction1.getDateTime().isAfter(now)) {
                            System.out.println(transaction1);
                        }
                    }
                    break;

                case "4": //Previous Year
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        String line;
                        bufferedReader.readLine();
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateformatter);
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    LocalDate firstDayCurrentYear = LocalDate.now().withDayOfYear(1);
                    LocalDateTime startOfPreviousYear = firstDayCurrentYear.minusYears(1).atStartOfDay();
                    LocalDateTime endOfPreviousYear = firstDayCurrentYear.minusDays(1).atTime(23, 59, 59);

                    System.out.println("===== PREVIOUS YEAR TRANSACTIONS =====");
                    for (Transaction transaction1 : list) {
                        if (!transaction1.getDateTime().isBefore(startOfPreviousYear) &&
                                !transaction1.getDateTime().isAfter(endOfPreviousYear)) {
                            System.out.println(transaction1);
                        }
                    }
                    break;

                case "5": // Search by Vendor
                    System.out.print("Enter the vendor name you want to search for:");
                    String searchVendor = scanner.nextLine().trim();

                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        String line;
                        bufferedReader.readLine(); // skip header
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateformatter);
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("===== SEARCH RESULTS FOR VENDOR: " + searchVendor + " =====");
                    boolean found = false;
                    for (Transaction transaction1 : list) {
                        if (transaction1.getVendor().equalsIgnoreCase(searchVendor)) {
                            System.out.println(transaction1);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("No transactions found for vendor: " + searchVendor);
                    }
                    break;

                case "6":
                    runCustomSearchMenu();
                case "0":
                    break;

                default:
                    System.out.println("Choose an option below.");
            }
        }
    }

    public static void runCustomSearchMenu() {
        ArrayList<Transaction> list = new ArrayList<>();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Scanner scanner = new Scanner(System.in);

        boolean search = true;

        // Temporary filter variables
        LocalDate startDate = null;
        LocalDate endDate = null;
        String searchDescription = "";
        String searchVendor = "";
        Double searchAmount = null;

        while (search) {
            System.out.println("\nCustom Search Options:");
            System.out.println("S) Start Date");
            System.out.println("E) End Date");
            System.out.println("D) Description");
            System.out.println("V) Vendor");
            System.out.println("A) Amount");
            System.out.println("X) Exit");
            System.out.print("Enter Choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "S":
                    System.out.print("Enter the start date (YYYY-MM-DD): ");
                    String startInput = scanner.nextLine().trim();
                    if (!startInput.isEmpty()) {
                        try {
                            startDate = LocalDate.parse(startInput, dateformatter);
                        } catch (Exception e) {
                            System.out.println("Invalid date format.");
                            break;
                        }
                    }

                    // Read file and filter immediately
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        bufferedReader.readLine();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime;
                            try {
                                transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateTimeFormatter);
                            } catch (Exception e) {
                                transactionDateTime = LocalDate.parse(parts[0], dateformatter).atStartOfDay();
                            }
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Print filtered transactions
                    System.out.println("\n===== START DATE FILTER RESULTS =====");
                    for (Transaction transaction : list) {
                        if (transaction.getDateTime().toLocalDate().isAfter(startDate.minusDays(1))) {
                            System.out.println(transaction);
                        }
                    }
                    break;

                case "E":
                    System.out.print("Enter the end date (YYYY-MM-DD): ");
                    String endInput = scanner.nextLine().trim();
                    if (!endInput.isEmpty()) {
                        try {
                            endDate = LocalDate.parse(endInput, dateformatter);
                        } catch (Exception e) {
                            System.out.println("Invalid date format.");
                            break;
                        }
                    }

                    // Read file and filter immediately
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        bufferedReader.readLine(); // skip header
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime;
                            try {
                                transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateTimeFormatter);
                            } catch (Exception e) {
                                transactionDateTime = LocalDate.parse(parts[0], dateformatter).atStartOfDay();
                            }
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Print filtered transactions
                    System.out.println("\n===== END DATE FILTER RESULTS =====");
                    for (Transaction t : list) {
                        if (t.getDateTime().toLocalDate().isBefore(endDate.plusDays(1))) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "D":
                    System.out.print("Enter description keyword: ");
                    searchDescription = scanner.nextLine().trim();

                    // Read file and filter immediately
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        bufferedReader.readLine();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime;
                            try {
                                transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateTimeFormatter);
                            } catch (Exception e) {
                                transactionDateTime = LocalDate.parse(parts[0], dateformatter).atStartOfDay();
                            }
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Print filtered transactions
                    System.out.println("\n===== DESCRIPTION FILTER RESULTS =====");
                    for (Transaction t : list) {
                        if (t.getDescription().toLowerCase().contains(searchDescription.toLowerCase())) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "V":
                    System.out.print("Enter vendor name: ");
                    searchVendor = scanner.nextLine().trim();

                    // Read file and filter immediately
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        bufferedReader.readLine();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime;
                            try {
                                transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateTimeFormatter);
                            } catch (Exception e) {
                                transactionDateTime = LocalDate.parse(parts[0], dateformatter).atStartOfDay();
                            }
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Print filtered transactions
                    System.out.println("\n===== VENDOR FILTER RESULTS =====");
                    for (Transaction t : list) {
                        if (t.getVendor().equalsIgnoreCase(searchVendor)) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "A":
                    System.out.print("Enter amount: ");
                    String amountInput = scanner.nextLine().trim();
                    if (!amountInput.isEmpty()) {
                        try {
                            searchAmount = Double.parseDouble(amountInput);
                        } catch (Exception e) {
                            System.out.println("Invalid number format.");
                            break;
                        }
                    }

                    // Read file and filter immediately
                    list.clear();
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        bufferedReader.readLine();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");
                            LocalDateTime transactionDateTime;
                            try {
                                transactionDateTime = LocalDateTime.parse(parts[0] + " " + parts[1], dateTimeFormatter);
                            } catch (Exception e) {
                                transactionDateTime = LocalDate.parse(parts[0], dateformatter).atStartOfDay();
                            }
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);
                            list.add(new Transaction(transactionDateTime, vendor, description, amount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Print filtered transactions
                    System.out.println("\n===== AMOUNT FILTER RESULTS =====");
                    for (Transaction t : list) {
                        if (t.getAmount() == searchAmount) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "X":
                    search = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}

