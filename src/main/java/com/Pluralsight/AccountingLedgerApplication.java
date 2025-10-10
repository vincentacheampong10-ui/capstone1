package com.Pluralsight;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

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

            HomeScreen(choice, scanner);
        }
    }

    private static void  HomeScreen(String choice, Scanner scanner) {
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
                try {
                    FileReader fileReader = new FileReader("transactions.csv");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;

                    bufferedReader.readLine();

                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                        String[] parts = line.split(Pattern.quote("|"));
                        break;
                    }

                } catch (IOException e) {
                    // display stack trace if there was an error
                    e.printStackTrace();
                }
            case "X":
                System.out.println("You have exited the application");
                break;

        }
    }
}
