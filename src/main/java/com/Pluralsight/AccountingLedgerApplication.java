package com.Pluralsight;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountingLedgerApplication {
    public static void main(String[] args) {

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
            System.out.print("Enter choice: ");

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

                    // Save the data in CSV format: BanksName|accountName|depositID|depositAmount
                    printWriter.println(paymentDate + "|" + accountNameForDebit + "|" + paymentId + "|" + paymentAmount);

                    printWriter.close();
                    System.out.println("Payment made successfully!");
                    break;

                } catch (IOException e) {
                    // display stack trace if there was an error
                    e.printStackTrace();
                }
            case "L":
                ledgerMenu();
                break;

        }
    }

    public static void ledgerMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean ledger = true;

        while (ledger) {
            System.out.println("\n===== LEDGER MENU =====");
            System.out.println("Choose an option:");
            System.out.println("A) All - Display all entries");
            System.out.println("D) Deposits- Display only the entries that are deposits into the\n" +
                    "account");
            System.out.println("P) Payments - Display only the negative entries (or payments)");
            System.out.println("R) Reports- A new screen that allows the user to run pre-defined\n" +
                    "reports or to run a custom search ");
            System.out.println("X) Exit");
            System.out.println("Enter Choice: ");

            String choice2 = scanner.nextLine().trim().toLowerCase();
            HomeScreen(choice2, scanner);

            switch (choice2) {
                ///  In case this option is choosen, do this.
                case "a":

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("deposits.csv"));
                        String line = reader.readLine();

                        if (line == null) {
                            System.out.println("There is no ledger available.");
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
                        System.out.println("Error: Could not read deposits.csv.");
                        ledger = false;
                    }
            }
        }
    }
}