# capstone-1
# 📒 Financial Transactions CLI Application

A **Java-based Command-Line Interface (CLI)** application designed to help users track financial transactions for personal or business use. This program allows users to **record deposits and payments**, **view categorized transaction history**, and generate **summary reports**—all stored in a CSV file.

---

## 🧰 Project Overview

- Built using Java
- CLI interface — no GUI required
- Stores transactions in a local `transactions.csv` file
- Includes reporting tools for **Month-to-Date**, **Previous Month**, and **Year-to-Date** insights

---

## 🚀 Features

- [x] **Add Deposit** – Records money coming in
- [x] **Add Payment** – Records money going out (automatically stored as negative values)
- [x] **View Ledger** – Filter by all entries, deposits only, or payments only
- [x] **Generate Reports**:
  - Month to Date
  - Previous Month
  - Year to Date
  - Search by Vendor
- [x] Persistent data saved in `data/transactions.csv`
- [x] Fully formatted console display of all transactions

---

## 📸 Screenshots

### 🔹 Main Menu

<img width="213" alt="Image" src="https://github.com/user-attachments/assets/9b4dfbb3-2d0f-4385-b8cb-fc071f90f312" />

### 🔹 Ledger Menu

<img width="292" alt="Image" src="https://github.com/user-attachments/assets/d01fa09e-fa31-467f-bed2-f8de4ae70d65" />

### 🔹 Reports Menu

<img width="284" alt="Image" src="https://github.com/user-attachments/assets/bac34803-afaa-483b-9a7a-620c96ad3ab4" />

---

## 🧠 Interesting Code Snippet

Here’s an example of how transactions are saved to a CSV file and stored in memory at the same time:

```java
public static void makeADeposit() {
    ...
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
    } catch (IOException e) {
        System.out.println("Error writing to transactions.csv file");
        throw new RuntimeException(e);
    }
}
