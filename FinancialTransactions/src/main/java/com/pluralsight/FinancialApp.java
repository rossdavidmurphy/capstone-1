package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FinancialApp {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

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
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
            String ledgerEntryAsString = String.format("%tF | %tT | %s | %s | %.2f", date, time, description, vendor, amount);

//            LedgerEntry ledgerEntry = new LedgerEntry(date, time, description, vendor, amount);
            bufferedWriter.write(ledgerEntryAsString);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void makeAPayment() {
        System.out.println("=== Payment Screen ===");
        System.out.println();
        System.out.println();
        System.out.println();
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
}
