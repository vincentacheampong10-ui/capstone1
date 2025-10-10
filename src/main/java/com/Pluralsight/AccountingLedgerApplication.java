package com.Pluralsight;

import java.util.Scanner;

public class AccountingLedgerApplication {
    public static void main(String[] args) {

        Scanner scanner= new Scanner(System.in);

        boolean homeScreen=true;

        while (homeScreen) {
            System.out.println("\n===== HOME SCREEN =====");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim().toUpperCase();
        }
    }
}
