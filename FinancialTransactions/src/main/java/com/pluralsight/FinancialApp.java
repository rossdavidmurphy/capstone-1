package com.pluralsight;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinancialApp {

    private static Scanner input = new Scanner(System.in);
    private static List<LedgerEntry> ledgerEntries = new ArrayList<>();

    public static void main(String[] args) {
        loadTransactions();

        boolean running = true;

        while (running) {
            // Make .csv an array
            displayMainMenu();
            int selectMenuOption = input.nextInt();
            input.nextLine();

            switch (selectMenuOption) {
                case 1:
                    makeADeposit();
                    break;
                case 2:
                    makeAPayment();
                    break;
                case 3:
                    viewLedger();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid menu option. Please try again.");
            }
        }
    }

    public static void displayMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("Press 1 to Make a Deposit");
        System.out.println("Press 2 to Make a Payment");
        System.out.println("Press 3 to View Ledger");
        System.out.println("Press 4 to Exit");
    }

    public static void makeADeposit() {
        System.out.println("=== Deposit Screen ===");

        System.out.println("Enter product description: ");
        String description = input.nextLine();

        System.out.println("Enter vendor name: ");
        String vendor = input.nextLine();

        System.out.print("Enter the amount to deposit: ");
        double amount = input.nextDouble();
        input.nextLine();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/transactions.csv", true));
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
            String ledgerEntryAsString = String.format("%tF | %tT | %s | %s | %.2f", date, time, description, vendor, amount);

            LedgerEntry ledgerEntry = new LedgerEntry(date, time, description, vendor, amount);
            bufferedWriter.write(ledgerEntryAsString);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to transactions.csv file");
            throw new RuntimeException(e);
        }
        promptReturnToMenu();
    }

    public static void makeAPayment() {
        System.out.println("=== Payment Screen ===");

        System.out.println("Enter product description: ");
        String description = input.nextLine();

        System.out.println("Enter vendor name: ");
        String vendor = input.nextLine();

        double amount = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter the payment amount: ");
            if (input.hasNextDouble()) {
                amount = input.nextDouble();
                input.nextLine();
                amount = -Math.abs(amount);
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.nextLine();
            }
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/transactions.csv", true));
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
            String ledgerEntryAsString = String.format("%tF | %tT | %s | %s | %.2f", date, time, description, vendor, amount);

            LedgerEntry ledgerEntry = new LedgerEntry(date, time, description, vendor, amount);
            bufferedWriter.write(ledgerEntryAsString);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to transactions.csv file");
            throw new RuntimeException(e);
        }
        promptReturnToMenu();
    }

    public static void viewLedger() {
        System.out.println("=== Ledger Screen ===");
        System.out.println();
        System.out.println();
        System.out.println();
        promptReturnToMenu();
    }

    private static void promptReturnToMenu() {
        System.out.println("\nPress Enter to Return to the Main Menu.");
        input.nextLine();
    }
    public static void loadTransactions() {
        ledgerEntries.clear();

        try (Scanner input = new Scanner(new File("data/transactions.csv"))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] tokens = line.split("\\s* | \\s*");

                if (tokens.length != 5) continue;
//                System.out.println("Entry omitted due to incomplete field entry.");

                LocalDate date = LocalDate.parse(tokens[0]);
                LocalTime time = LocalTime.parse(tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);

                LedgerEntry entry = new LedgerEntry(date, time, description, vendor, amount);
                ledgerEntries.add(entry);
            }
        } catch (Exception e) {
            System.out.println("Could not load transactions.");
            throw new RuntimeException(e);
        }
    }
}
