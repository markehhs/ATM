import java.util.ArrayList;

public class Account {
    private String name;
    //private double balance;
    private String AID;
    private User holder;
    private ArrayList<Transaction> Transactions;

    public Account() {
        //default constructor
    }

    public Account(String name, User holder, Bank currBank) {
        //set the name and account holder
        this.name = name;
        this.holder = holder;

        //now lets generate a unique AID
        //this will be based off of currBank
        this.AID = currBank.genAID();

        //now lets init transactions
        //empty ArrayList
        this.Transactions = new ArrayList<Transaction>();
    }

    public String getAID() {
        return this.AID;
    }

    public String getSummaryLine() {
        //lets the the balance of the account
        double balance = this.getBalance();

        //format the summary line
        //positive or negative
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.AID, balance, this.name);
        } else {
            //negative
            return String.format("%s : $-%.02f : %s", this.AID, balance, this.name);
        }
    }

    public double getBalance() {
        double balance = 0;
        //here we will loop through the transactions
        //and calculate the balance
        for (Transaction t : this.Transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.AID);
        for ( int i = this.Transactions.size()-1; i >= 0; i--) {
            //loop through the transactions starting from the most recent transaction
            System.out.println(this.Transactions.get(i).getSummaryLine());
        }
        System.out.println("");
    }

    public void addTransaction(double amount) {
        //create a transaction object and add to the list
        Transaction newTransaction = new Transaction(amount, this);
        this.Transactions.add(newTransaction);
    }

}