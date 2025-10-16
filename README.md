The Accounting Ledger Application is a Java-based console program that allows users to record, view, and analyze financial transactions such as deposits and payments. It reads and writes transaction data to CSV files, providing a simple yet functional way to manage a personal or small-business ledger.

This project demonstrates practical use of file handling, date/time manipulation, loops, and object-oriented programming (OOP) in Java.

⚙️ Features
🏠 Home Screen

The main menu allows users to:

D) Add a deposit

P) Make a payment (debit)

L) View the ledger (transaction records)

X) Exit the application

💰 Deposits & Payments

Users can record:

Deposits → Saved in deposits.csv

Payments → Saved in Payment.csv

Each record includes details such as date, account name, transaction ID, and amount.

📜 Ledger Menu

The ledger allows users to view and filter transaction records:

A) Display all transactions (transactions.csv)

D) View only deposits

P) View only payments

R) Access advanced reports

X) Exit back to the home screen

📊 Reports Menu

Users can generate time-based and vendor-specific reports:

Month To Date – Shows all transactions from the current month

Previous Month – Filters only last month’s transactions

Year To Date – Displays all transactions since the start of the year

Previous Year – Shows last year’s transactions

Search by Vendor – Finds transactions by vendor name

Exit – Returns to the previous menu

🧠 Technical Concepts Used

File I/O (FileReader, FileWriter, BufferedReader, PrintWriter)

Java Collections (ArrayList)

Date and Time API (LocalDate, LocalDateTime, DateTimeFormatter)

Conditional logic & loops (switch, while)

Encapsulation via the Transaction class
