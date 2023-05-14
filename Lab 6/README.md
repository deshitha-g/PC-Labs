# PC-Lab-6

Suppose you are asked to simulate an ATM for ABC Bank. For each client, ABC Bank keeps a record of the client’s id, name, 
nationality, occupation, address, age, and gender. A client should obtain a PIN (Personal Identification Number) to access the ATM. 
ABC offers saving accounts, current accounts, and loans for its clients. A client can have multiple bank accounts. 
Each account has an account number, the currency in which the account is operated, the branch, and the balance. 
The bank pays interest for saving accounts. ABC has a restriction that all the accounts of a given client should be 
operated in a single currency. A client with any bank account can request a loan, and a loan is linked to an account. 
A loan has the amount, interest, duration, and payment method.

Using the ATM, the clients of ABC Bank can do many transactions. Each transaction is recorded with a transaction id, bank account id, 
date, and status (if the transaction is a success, failure, or cancellation). The possible transactions are deposits, withdrawals, 
and balance inquiries. The transaction amount should be recorded for deposits and withdrawals, whereas the balance should be recorded for balance inquiries.

Upon visiting the website of the ABC ATM, the client should experience the following sequence of events.

* First of all the client is shown “Welcome!” and asked to enter the PIN.
* Then the client is shown his accounts in ABC Bank and asked to select one account to proceed.
* Then the client is shown the main menu below:

      Main Menu
      1. View Balance.
      2. Withdraw money.
      3. Deposit money.
      4. Exit.

The client will enter the choice. If Option 2 or 3 is selected, the client will be asked to enter the amount, 
and once the transaction is completed, the remaining balance should be displaced.

Task :
  Implement the Java code for the ATM machine of the ABC bank. Make sure your code is readable with comments and proper names classes, variables and methods.
