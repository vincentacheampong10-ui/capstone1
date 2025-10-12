package com.Pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AccountingLedgerApplication {
    public static void main(String[] args) {

        Transaction t = new Transaction();
        Scanner scanner = new Scanner(System.in);

        boolean homeScreen = true;
        boolean ledger = true;


        while (homeScreen) {
            System.out.println("\n===== HOME SCREEN =====");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice:");

            String choice1 = scanner.nextLine().trim().toUpperCase();

            HomeScreen(choice1, scanner);

        }
    }

    private static void HomeScreen(String choice1, Scanner scanner) {
        switch (choice1) {
            ///  In case this option is choosen, do this.
            case "D":
                System.out.print("Enter deposit date (YYYY-MM-DD): ");
                String BanksName = scanner.nextLine();

                System.out.print("Enter account holder name: ");
                String accountName = scanner.nextLine();

                System.out.print("Enter deposit ID: ");
                int depositID = scanner.nextInt();

                System.out.print("Enter deposit amount: ");
                int depositAmount = scanner.nextInt();

                try {
                    FileWriter fileWriter = new FileWriter("deposits.csv", true); // 'true' for append mode
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    // Save the data in CSV format: BanksName|accountName|depositID|depositAmount
                    printWriter.println(BanksName + "|" + accountName + "|" + depositID + "|" + depositAmount);

                    printWriter.close();
                    System.out.println("Deposit saved successfully!");
                    break;

                } catch (IOException e) {
                    // display stack trace if there was an error
                    e.printStackTrace();
                }
            case "P":
                System.out.print("Enter payment date (YYYY-MM-DD): ");
                String paymentDate = scanner.nextLine();

                System.out.print("Enter account name: ");
                String accountNameForDebit = scanner.nextLine();

                System.out.print("Enter Payment ID: ");
                int paymentId = scanner.nextInt();

                System.out.print("Enter Payment amount: ");
                int paymentAmount = scanner.nextInt();

                try {
                    FileWriter fileWriter = new FileWriter("Payment.csv", true); // 'true' for append mode
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    // Save the data in CSV format: paymentDate|accountNameForPayment|paymentID|paymentAmount
                    printWriter.println(paymentDate + "|" + accountNameForDebit + "|" + paymentId + "|" + paymentAmount);

                    printWriter.close();
                    System.out.println("Payment made successfully!");
                    break;

                } catch (IOException e) {
                    // display stack trace if there was an error
                    e.printStackTrace();
                }
            case "L":
                ledgerMenu(scanner);
                break;

        }
    }

    public static void ledgerMenu(Scanner scanner) {
//        Scanner scanner = new Scanner(System.in);
        boolean ledger = true;

        while (ledger) {
            System.out.println("\n===== LEDGER MENU =====");
            System.out.println("Choose an option:");
            System.out.println("A) All - Display all entries");
            System.out.println("E) Deposits - Only deposits");
            System.out.println("F) Payments - Only payments");
            System.out.println("R) Reports- Custom Search");
            System.out.println("X) Exit");
            System.out.print("Enter Choice: ");

            String choice2 = scanner.nextLine().trim().toUpperCase();
//            ledger(choice2, scanner);

            switch (choice2) {
                ///  In case this option is choosen, do this.
                case "E":
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("deposits.csv"));
                        String line = reader.readLine();//Mma weremmfire se woebekasa afaho

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
                case "F":
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("Payment.csv"));
                        String line = reader.readLine();//Mma weremmfire se woebekasa afaho

                        if (line == null) {
                            System.out.println("There is no payment available.");
                            ledger = false; // exit ledger
                        } else {

                            System.out.println(line);

                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split("\\|");
                                if (parts.length >= 4) {
                                    // Get the amount part and remove spaces
                                    String amountText = parts[3].trim();
                                    double amount = Double.parseDouble(amountText);
                                    if (amount < 0) {
                                        System.out.println(line);
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
                case "R":
                    reportsMenu(scanner);
                    break;


            }
        }
    }

    public static void reportsMenu(Scanner scanner) {
        Transaction transaction= new Transaction();
        List<Transaction> list=new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate  dateTime= LocalDate.now();


        boolean reports = true;

        while (reports) {
            System.out.println("\n===== REPORTS MENU =====");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Exit");
            System.out.print("Enter Choice:");
            String choice3 = scanner.nextLine().trim();

            switch (choice3) {
                ///  In case this option is choosen, do this.
                case "1":
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"))) {
                        String line;
                        bufferedReader.readLine(); // skip header line

                        while ((line = bufferedReader.readLine()) != null) {
                            String[] parts = line.split("\\|");

                            //assigning locations of date|time|description|vendor|amount
                            LocalDate date = LocalDate.parse(parts[0], formatter);
                            String description = parts[2];
                            String vendor = parts[3];
                            double amount = Double.parseDouble(parts[4]);

//                            // This basically holds the list so that its printed
                            Transaction transaction1 = new Transaction(date, description, vendor, amount);
                            list.add(transaction1);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    LocalDate today = LocalDate .now();
                    LocalDate firstOfMonth = today.withDayOfMonth(1);

                    System.out.println("===== MONTH TO DATE TRANSACTIONS =====");

                    for (Transaction transaction1 : list) {
                        if (!transaction1.getDateTime().isBefore(firstOfMonth) && !transaction1.getDateTime().isAfter(today)) {
                            System.out.println(transaction);
                        }
                    }

//
            }
        }
    }
}