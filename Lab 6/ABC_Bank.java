import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;
import java.time.LocalDate;

class Client {
    int id;
    String name;
    String nationality;
    String occupation;
    String address;
    int age;
    String gender;
    String currency;
    private int pin;
    private static int countClientID = 1;
    public static Vector<Client> clientList = new Vector<>();
    public Vector<Account> accountList = new Vector<>();

    // this is the constructor of client class
    public Client(String name, String nationality, String occupation, String address, int age, String gender, String currency, int pin) {
        // 0 to 999 numbers were used as the client ID respectively
        this.id = countClientID;
        countClientID++;

        this.name = name;
        this.nationality = nationality;
        this.occupation = occupation;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.currency = currency;
        this.pin = pin;

        // add newly created client to the clientList
        clientList.add(this);
    }

    public int getPin() {
        return pin;
    }

    // Client can change the pin
    public void setPin(int pin) {
        this.pin = pin;
    }
}

abstract class Account {
    private int accountNumber;
    private String currency;
    private String branch;
    private double balance;
    private static int countAccountID = 1000;

    // constructor for Account class
    public Account(Client client, String branch, double balance) {
        // 1000 to 1999 numbers were used as Account ID respectively
        this.accountNumber = countAccountID;
        countAccountID++;

        this.currency = client.currency;
        this.branch = branch;
        this.balance = balance;

        // add newly created account to the accountList
        client.accountList.add(this);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getBranch() {
        return branch;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class SavingsAccount extends Account {
    // constructor for SavingsAccount Class
    public SavingsAccount(Client client, String branch, double balance) {
        super(client, branch, balance);
    }

    public void payInterest(double interestRate) {
        double interest = this.getBalance()*(interestRate/100);
        this.setBalance(this.getBalance()+interest);
        System.out.println(interest + "interest paid.");
    }
}

class CurrentAccount extends Account {
    // constructor for CurrentAccount class
    public CurrentAccount(Client client, String branch, double balance) {
        super(client, branch, balance);
    }
}

class Loan {
    private Account account;
    private double interest;
    private int duration;  // duration in years
    private String paymentMethod;

    // constructor for Loan class
    public Loan(Account account, double interest, int duration, String paymentMethod) {
        this.account = account;
        this.interest = interest;
        this.duration = duration;
        this.paymentMethod = paymentMethod;
    }

    public Account getAccount() {
        return account;
    }

    public double getInterest() {
        return interest;
    }

    public int getDuration() {
        return duration;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}

abstract class Transaction {
    private int transactionId;
    private int bankAccountId;
    private LocalDate date;
    private String status;
    private static int countTransactionID = 2000;

    // constructor for Transaction class
    public Transaction(Account account, LocalDate date) {
        // from 2000 onwards numbers were used as Transaction ID
        this.transactionId = countTransactionID;
        countTransactionID++;

        this.bankAccountId = account.getAccountNumber();
        this.date = date;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // method for display the transaction receipt
    public void displayReceipt() {
        System.out.println("Transaction Receipt:");
        System.out.println("\tTransaction ID : " + this.transactionId);
        System.out.println("\tBank Account ID : " + this.bankAccountId);
        System.out.println("\tDate : " + this.date);
        System.out.println("\tStatus : " + this.status);
    }
}

class Deposit extends Transaction {
    // constructor for Deposit class
    public Deposit(Account account, LocalDate date, double amount) {
        super(account, date);
        this.setStatus("Success");

        account.setBalance(account.getBalance()+amount);

        this.displayReceipt();
        System.out.println("\tCurrent Balance : " + account.getBalance());
    }
}

class Withdraw extends Transaction {
    // constructor for Withdraw class
    public Withdraw(Account account, LocalDate date, double amount) {
        super(account, date);
        if (amount > account.getBalance()) {
            this.setStatus("Failure");
        } else {
            this.setStatus("Success");
            account.setBalance(account.getBalance()-amount);
        }
        this.displayReceipt();
        System.out.println("\tCurrent Balance : " + account.getBalance());
    }
}

class BalanceInquiry extends Transaction {
    // constructor for BalanceInquiry class
    public BalanceInquiry(Account account, LocalDate date) {
        super(account, date);
        this.setStatus("Success");
        System.out.println("Account Balance = " + account.getBalance());
        this.displayReceipt();
    }
}

public class Main {
    public static void main(String[] args) {
        // you should set the current day here
        LocalDate today = LocalDate.now();

        // you can create Clients and Accounts in this area

        //This is just an example for creation of objects. If you want, you can uncomment those to check the code.

        Client client1 = new Client("Chamara", "Sinhalese", "Teacher", "23/1, Matara.", 28, "Male", "LKR", 1256);
        Client client2 = new Client("Fathima", "Muslim", "Doctor", "Molpe, Katubedda, Moratuwa.", 31, "Female", "LKR", 6023);

        SavingsAccount account1 = new SavingsAccount(client1, "Matara", 20549.23);
        CurrentAccount account2 = new CurrentAccount(client1, "Moratuwa", 101563.84);
        SavingsAccount account3 = new SavingsAccount(client1, "Katubedda", 45124.15);
        SavingsAccount account4 = new SavingsAccount(client2, "Katubedda", 541621.23);
        CurrentAccount account5 = new CurrentAccount(client2, "Colombo-07", 101236.14);


        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome!");

        System.out.print("Enter your ID : ");
        int clientID = sc.nextInt();

        boolean flag = true;
        for (Client i: Client.clientList) {
            if (i.id == clientID) {
                System.out.print("Enter your PIN number : ");
                int pin = sc.nextInt();

                if (pin == i.getPin()) {
                    boolean run = true;
                    while (run) {
                        System.out.println("\nPlease select one account to proceed.");

                        int count = 1;
                        for (Account a : i.accountList) {
                            System.out.println(count + ". " + a.getAccountNumber());
                            count++;
                        }

                        System.out.print("Enter your choise : ");
                        Account selectedAccount = i.accountList.get(sc.nextInt() - 1);

                        System.out.println("\n1. View Balance \n2. Withdraw Money \n3. Deposit Money \n4. Exit");
                        System.out.print("Enter your choise : ");
                        int choise = sc.nextInt();
                        System.out.println();

                        if (choise == 1) {
                            BalanceInquiry newInquiry = new BalanceInquiry(selectedAccount, today);
                        } else if (choise == 2) {
                            System.out.print("Enter the withdraw amount : ");
                            double amount = sc.nextDouble();
                            Withdraw newWithdraw = new Withdraw(selectedAccount, today, amount);
                        } else if (choise == 3) {
                            System.out.print("Enter the deposit amount : ");
                            double amount = sc.nextDouble();
                            Deposit newDeposit = new Deposit(selectedAccount, today, amount);
                        } else if (choise == 4) {
                            System.out.println("Exit...!");
                        } else {
                            System.out.println("Invalid Choise!");
                        }

                        System.out.print("\nDo you need another service? (Yes/No) : ");
                        String input = sc.next();
                        if (Objects.equals(input, "Yes")) {
                            continue;
                        }
                        else if (Objects.equals(input, "No")){
                            run = false;
                            System.out.println("Thank You!");
                        } else {
                            run = false;
                            System.out.println("Invalid input.\nThank You!");
                        }
                    }

                } else {
                    System.out.println("\nAccess denied! Invalid pin number!");
                }

                flag = false;
                break;
            }
        }

        if (flag) {
            System.out.println("\nInvalid client ID.");
        }

    }
}
