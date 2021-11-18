import java.util.Scanner;
public class Atm {
    public static void main(String[] args) {
        //main method for the ATM project.
        Scanner scan = new Scanner(System.in);
        //this will be the bank
        Bank currBank = new Bank("Chicago Bank");

        //add a new user to the bank which also creates a savings account
        User user1 = currBank.addUser("Mark", "Strainis", "1234");
        
        //now lets create an account for the user
        Account newAccount = new Account("Checking", user1, currBank);
        user1.addAccount(newAccount);//add the account to the user object
        currBank.addAccount(newAccount);//add the account to the bank object

        User currUser;
        while (true) {
            //login prompt until succesful login
            currUser = Atm.mainMenuPrompt(currBank, scan);
            //stay in the menu until the user quits
            Atm.printUserMenu(currUser, scan);
        }
    }

    public static User mainMenuPrompt(Bank currBank, Scanner scan) {
        String UID;
        String pin;
        User authUser;

        //ask for ID/pin until correct
        do {
            System.out.println(currBank.getName());
            System.out.print("Enter UID:");
            UID = scan.nextLine();
            System.out.print("\nEnter pin: ");
            pin = scan.nextLine();
            //now lets attempt to get the user object that corresponds to this combo
            authUser = currBank.login(UID, pin);
            if (authUser == null) {
                System.out.println("Incorrect UID/pin please try again.");
            }
        } while(authUser == null);//continue this loop until we find the user

        return authUser;//logged in so return the user.
    }

    public static void printUserMenu(User currUser, Scanner scan) {
        //lets print a summary of all accounts for this user...
        currUser.printAccountsSummary();
        //
        int selection;
        //user menu
        do {
            System.out.println("Hello " + currUser.getFName() + ", please make a selection.");
            System.out.println("1: Display transactions.");
            System.out.println("2: Withdraw.");
            System.out.println("3: Deposit.");
            System.out.println("4: Transfer.");
            System.out.println("5: Exit.");
            System.out.println("\nSelection: ");
            selection = scan.nextInt();

            if (selection < 1 || selection > 5) {
                System.out.println("Invalid selection. Please make a selection that is 1-5.");
            }
        } while (selection < 1 || selection > 5);

        //succesful selection
        switch (selection) {
            case 1:
                Atm.showTransactions(currUser, scan);
                break;
            case 2:
                Atm.withdraw(currUser, scan);
                break;
            case 3:
                Atm.deposit(currUser, scan);
                break;
            case 4:
                Atm.transfer(currUser, scan);
                break;
            case 5:
                scan.nextLine();
                break;
        }

        //here we can just keep displaying the menu unless the selection is 5
        if (selection != 5) {
            Atm.printUserMenu(currUser, scan);
        }

        }

        public static void showTransactions(User currUser, Scanner scan) {
            int theAcct;

            do {
                //get the account whos transaction history we want to look up
                System.out.printf("Enter a number (1-%d) of the account whose transactions you want to see.",
                currUser.numAccounts());
                theAcct = scan.nextInt()-1;
                if (theAcct < 0 || theAcct >= currUser.numAccounts()) {
                    System.out.println("Invalid account. Please make another selection.");
                }
            } while (theAcct < 0 || theAcct >= currUser.numAccounts());
            //we have the correct account so lets print the transactions
            currUser.printAcctTransactionHistory(theAcct);
        }
    
        public static void transfer(User currUser, Scanner scan) {
            //
            int fromAcct;
            int toAcct;
            double amount;
            double acctBalance;

            //lets get the account that we are transfering FROM
            do {
                System.out.printf("Enter the number (1-%d) of the account to transfer from: ", currUser.numAccounts());
                fromAcct = scan.nextInt()-1;
                if (fromAcct < 0 || fromAcct >= currUser.numAccounts()) {
                    System.out.println("Invalid account selection please try again.");
                }
            } while (fromAcct < 0 || fromAcct >= currUser.numAccounts());
            //out of the loop so we know the account is correct
            acctBalance = currUser.getAcctBalance(fromAcct);

            //now lets get the account to transfer too
            do {
                System.out.printf("Enter the number (1-%d) of the account to transfer to: ", currUser.numAccounts());
                toAcct = scan.nextInt()-1;
                if (toAcct < 0 || toAcct >= currUser.numAccounts()) {
                    System.out.println("Invalid account selection please try again.");
                }
            } while (toAcct < 0 || toAcct >= currUser.numAccounts());
            //out of the loop so we know the account is correct

            //get the amount to transfer
            do {
                System.out.printf("Enter the amount to transfer (Max amount: $%.02f)", acctBalance);
                amount = scan.nextInt();

                if (amount < 0) {
                    //dont allow negatives
                    System.out.println("Amount must be greater than 0.");
                } else if (amount > acctBalance) {
                    System.out.printf("Amount can't exceed: $%.02f\n", acctBalance);
                }
            } while(amount < 0 || amount > acctBalance);
            //out of the loop so lets do the transfer
            currUser.addAcctTransaction(fromAcct, -1*amount);
            currUser.addAcctTransaction(toAcct, amount);
            
        }

        public static void withdraw(User currUser, Scanner scan) {
            //
            int fromAcct;
            double amount;
            double acctBalance;

            //lets get the account that we are transfering FROM
            do {
                System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", currUser.numAccounts());
                fromAcct = scan.nextInt()-1;
                if (fromAcct < 0 || fromAcct >= currUser.numAccounts()) {
                    System.out.println("Invalid account selection please try again.");
                }
            } while (fromAcct < 0 || fromAcct >= currUser.numAccounts());
            //out of the loop so we know the account is correct
            acctBalance = currUser.getAcctBalance(fromAcct);

            //get the amount to transfer
            do {
                System.out.printf("Enter the amount to withdraw (Max amount: $%.02f) ", acctBalance);
                amount = scan.nextDouble();

                if (amount < 0) {
                    //dont allow negatives
                    System.out.println("Amount must be greater than 0.");
                } else if (amount > acctBalance) {
                    System.out.printf("Amount can't exceed: $%.02f\n", acctBalance);
                }
            } while(amount < 0 || amount > acctBalance);
            //get the rest of the input
            scan.nextLine();
            //now lets make the withdraw
            currUser.addAcctTransaction(fromAcct, -1*amount);
        }

        public static void deposit(User currUser, Scanner scan) {
            //
            int toAcct;
            double amount;
            double acctBalance;

            //lets get the account that we are transfering FROM
            do {
                System.out.printf("Enter the number (1-%d) of the account to deposit to: ", currUser.numAccounts());
                toAcct = scan.nextInt()-1;
                if (toAcct < 0 || toAcct >= currUser.numAccounts()) {
                    System.out.println("Invalid account selection please try again.");
                }
            } while (toAcct < 0 || toAcct >= currUser.numAccounts());
            //out of the loop so we know the account is correct
            acctBalance = currUser.getAcctBalance(toAcct);

            //get the amount to transfer
            do {
                System.out.printf("Enter the amount to deposit (Max amount: $%.02f) ", acctBalance);
                amount = scan.nextDouble();

                if (amount < 0) {
                    //dont allow negatives
                    System.out.println("Amount must be greater than 0.");
                }
            } while(amount < 0);
            //get the rest of the input
            scan.nextLine();
            //now lets make the deposit
            currUser.addAcctTransaction(toAcct, amount);

        }
}