import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private double balance;
    private String pin;
    private List<String> transactionHistory;
    private Scanner scanner;

    public ATM(double initialBalance, String initialPin) {
        this.balance = initialBalance;
        this.pin = initialPin;
        this.transactionHistory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Method to start ATM service with PIN verification
    public void start() {
        System.out.print("Enter your PIN: ");
        String enteredPin = scanner.nextLine();

        if (verifyPin(enteredPin)) {
            showMenu();
        } else {
            System.out.println("Incorrect PIN. Access denied.");
        }
    }

    // Verify entered PIN
    private boolean verifyPin(String enteredPin) {
        return enteredPin.equals(this.pin);
    }

    // Show the main menu
    private void showMenu() {
        while (true) {
            System.out.println("===== ATM Menu =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Change PIN");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        showTransactionHistory();
                        break;
                    case 5:
                        changePin();
                        break;
                    case 6:
                        System.out.println("Exiting. Thank you for using our ATM!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // Check current balance
    private void checkBalance() {
        System.out.println("Current Balance: $" + balance);
    }

    // Deposit money into the account
    private void deposit() {
        try {
            System.out.print("Enter amount to deposit: $");
            double amount = scanner.nextDouble();

            if (amount > 0) {
                balance += amount;
                transactionHistory.add("Deposited: $" + amount);
                System.out.println("Successfully deposited $" + amount);
            } else {
                System.out.println("Invalid deposit amount.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    // Withdraw money from the account
    private void withdraw() {
        try {
            System.out.print("Enter amount to withdraw: $");
            double amount = scanner.nextDouble();

            if (amount > 0 && amount <= balance) {
                balance -= amount;
                transactionHistory.add("Withdrew: $" + amount);
                System.out.println("Successfully withdrew $" + amount);
            } else if (amount > balance) {
                System.out.println("Insufficient balance.");
            } else {
                System.out.println("Invalid withdrawal amount.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.nextLine(); // Clear the invalid input
        }
    }

    // Display transaction history
    private void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("===== Transaction History =====");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    // Change the ATM PIN
    private void changePin() {
        System.out.print("Enter current PIN: ");
        String currentPin = scanner.nextLine();

        if (verifyPin(currentPin)) {
            System.out.print("Enter new PIN: ");
            String newPin = scanner.nextLine();
            System.out.print("Confirm new PIN: ");
            String confirmPin = scanner.nextLine();

            if (newPin.equals(confirmPin) && newPin.length() == 4) {
                this.pin = newPin;
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("PINs do not match or invalid PIN format. Try again.");
            }
        } else {
            System.out.println("Incorrect current PIN.");
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM(500.00, "1234"); // Starting with a balance of $500 and default PIN "1234"
        atm.start();
    }
}
