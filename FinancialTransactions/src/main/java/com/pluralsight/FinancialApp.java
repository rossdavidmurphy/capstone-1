package com.pluralsight;

import com.sun.source.tree.IfTree;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
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
                    viewLedgerMenu();
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
        System.out.println("\n=== Main Menu ===");
        System.out.println("Press 1 to Make a Deposit");
        System.out.println("Press 2 to Make a Payment");
        System.out.println("Press 3 to View Ledger");
        System.out.println("Press 4 to Exit");
    }
    public static void makeADeposit() {
        System.out.println("\n=== Deposit Screen ===");

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
            String ledgerEntryAsString = String.format("%tF | %tT | %s | %s | %.2f", date, time, description, vendor, amount);

            LedgerEntry ledgerEntry = new LedgerEntry(date, time, description, vendor, amount);
            ledgerEntries.add(ledgerEntry);
            bufferedWriter.write(ledgerEntryAsString);
            bufferedWriter.newLine();
            bufferedWriter.close();
            promptReturnToMainMenu();
        } catch (IOException e) {
            System.out.println("Error writing to transactions.csv file");
            throw new RuntimeException(e);
        }
    }
    public static void makeAPayment() {
        System.out.println("\n=== Payment Screen ===");

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
            String ledgerEntryAsString = String.format("%tF | %tT | %s | %s | %.2f", date, time, description, vendor, amount);

            LedgerEntry ledgerEntry = new LedgerEntry(date, time, description, vendor, amount);
            ledgerEntries.add(ledgerEntry);
            bufferedWriter.write(ledgerEntryAsString);
            bufferedWriter.newLine();
            bufferedWriter.close();
            promptReturnToMainMenu();
        } catch (IOException e) {
            System.out.println("Error writing to transactions.csv file");
            throw new RuntimeException(e);
        }
    }

    public static void viewLedgerMenu() {

        boolean displayLedgerMenu = true;

        while (displayLedgerMenu) {
            System.out.println("\n=== Ledger Menu ===");
            System.out.println("Press 1 to Display All Entries");
            System.out.println("Press 2 to Display Deposits");
            System.out.println("Press 3 to Display Payments");
            System.out.println("Press 4 to Display Reports");
            System.out.println("\nPress Enter to Return to Main Menu");

            String selectMenuOption = input.nextLine();

            switch (selectMenuOption) {
                case "1":
                    displayAllEntries();
                    break;
                case "2":
                    displayDeposits();
                    break;
                case "3":
                    displayPayments();
                    break;
                case "4":
                    viewReportMenu();
                    break;
                case "":
                    displayLedgerMenu = false;
                    break;
                default:
                    System.out.println("Invalid menu option. Please try again.");
            }
        }
    }
    public static void displayAllEntries() {
        System.out.println("\n=== All Entries ===");

        for (LedgerEntry entry : ledgerEntries) {
            System.out.println(entry.display());
        }
    }
    public static void displayDeposits() {
        System.out.println("\n=== Deposit Entries ===");
        for (LedgerEntry entry : ledgerEntries) {
            if (entry.getAmount() > 0) {
                System.out.println(entry.display());
            }
        }
    }
    public static void displayPayments() {
        System.out.println("\n=== Payment Entries ===");
        for (LedgerEntry entry : ledgerEntries) {
            if (entry.getAmount() <= 0) {
                System.out.println(entry.display());
            }
        }
    }

    private static void viewReportMenu() {

        boolean displayReportMenu = true;

        while (displayReportMenu) {
            System.out.println("\n=== Reports Menu ===");
            System.out.println("Press 1 to Display Month to Date");
            System.out.println("Press 2 to Display Previous Month");
            System.out.println("Press 3 to Display Year to Date");
            System.out.println("Press 4 to Search by Vendor");
            System.out.println("\nPress Enter to Return to Main Menu");

            String selectMenuOption = input.nextLine();

            switch (selectMenuOption) {
                case "1":
                    displayMonthToDate();
                    break;
                case "2":
                    displayPreviousMonth();
                    break;
                case "3":
                    displayYearToDate();
                    break;
                case "4":
                    searchByVendor();
                    break;
                case "":
                    displayReportMenu = false;
                    break;
                default:
                    System.out.println("Invalid menu option. Please try again.");
            }
        }
    }
    public static void displayMonthToDate() {
        System.out.println("\n=== Month to Date ===");

        LocalDate firstOfTheMonth = LocalDate.now().withDayOfMonth(1).minusDays(1);
        LocalDate currentDate =  LocalDate.now().plusDays(1);

        for (LedgerEntry entry : ledgerEntries) {
            boolean withinTimeframe = entry.date.isAfter(firstOfTheMonth) &&  entry.date.isBefore(currentDate);
            if (withinTimeframe) {
                System.out.println(entry.display());
            }
        }
    }
    public static void displayPreviousMonth() {
        System.out.println("\n=== Previous Month ===");
        YearMonth previousMonth = YearMonth.now().minusMonths(1);

        for (LedgerEntry entry : ledgerEntries) {
            LocalDate entryDate = entry.date;

            YearMonth entryMonth = YearMonth.from(entryDate);

            if (entryMonth.equals(previousMonth)) {
                System.out.println(entry.display());
            }
        }
    }
    public static void displayYearToDate() {
        System.out.println("\n=== Year to Date ===");

        LocalDate firstOfTheYear = LocalDate.now().withDayOfYear(1).minusYears(1);
        LocalDate currentDate = LocalDate.now();

        for (LedgerEntry entry : ledgerEntries) {
            boolean withinTimeframe = entry.date.isAfter(firstOfTheYear) && entry.date.isBefore(currentDate);
            if (withinTimeframe) {
                System.out.println(entry.display());
            }
        }
    }
    public static void searchByVendor() {
        System.out.println("Please Enter the Vendor Name");

        String searchEntry = input.nextLine();

        boolean found = false;

        for (LedgerEntry entry : ledgerEntries) {
            if (entry.getVendor().equalsIgnoreCase(searchEntry)) {
                System.out.println(entry.display());
                found = true;
            }
        }
        if (!found){
            System.out.println("No transaction found for: " + searchEntry);
        }

    }

    private static void promptReturnToMainMenu() {
        System.out.println("\nPress Enter to Return to the Main Menu.");
        input.nextLine();
    }

    public static void loadTransactions() {
        ledgerEntries.clear();

        try (Scanner input = new Scanner(new File("data/transactions.csv"))) {
            if (input.hasNextLine()) {
                input.nextLine();
            }
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] tokens = line.split("\\| ");

                if (tokens.length != 5) continue;

                LocalDate date = LocalDate.parse(tokens[0].trim());
                LocalTime time = LocalTime.parse(tokens[1].trim());
                String description = tokens[2].trim();
                String vendor = tokens[3].trim();
                double amount = Double.parseDouble(tokens[4].trim());

                LedgerEntry entry = new LedgerEntry(date, time, description, vendor, amount);
                ledgerEntries.add(entry);
            }

            System.out.println("Loaded " + ledgerEntries.size() + " transactions.");
        } catch (Exception e) {
            System.out.println("Could not load transactions.");
            throw new RuntimeException(e);
        }
    }
}
