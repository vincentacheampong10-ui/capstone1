package com.Pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AccountingLedgerApplication {
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
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
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
                case "p":
                    System.out.print("Enter payment date (YYYY-MM-DD): ");
                    String paymentDate = scanner.nextLine();

                    System.out.print("Enter account name: ");
                    String accountNameForDebit = scanner.nextLine();

                    System.out.print("Enter deposit ID: ");
                    int depositIDForDebit = scanner.nextInt();

                    System.out.print("Enter deposit amount: ");
                    int depositAmountForDebit = scanner.nextInt();

                    try {
                        FileWriter fileWriter = new FileWriter("MakePayment(Debit).csv", true); // 'true' for append mode
                        PrintWriter printWriter = new PrintWriter(fileWriter);

                        // Save the data in CSV format: BanksName|accountName|depositID|depositAmount
                        printWriter.println(paymentDate + "|" + accountNameForDebit + "|" + depositIDForDebit + "|" + depositAmountForDebit);

                        printWriter.close();
                        System.out.println("Payment made successfully!");
                        break;

                    } catch (IOException e) {
                        // display stack trace if there was an error
                        e.printStackTrace();
                    }

            }
        }
    }
}
