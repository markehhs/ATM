import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String fName;//last name
    private String lName;//first name
    private String UID;//user ID (unique)
    private byte pinCode[];//pin code (hashed)
    private ArrayList<Account> Accounts;//list of accounts owned by the user
    private Bank bank;//bank that the user is associated with

    public User() {
        //default constructor
    }

    public User(String fName, String lName, String pin, Bank currBank) {
        //set the first and last names
        this.fName = fName;
        this.lName = lName;
        //now lets handle the pin
        //we will use MD5 to hash it
        
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            this.pinCode = md5.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            // error 
            System.err.println("ERROR: NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //now lets generate a UID
        //this will be based off of currBank
        this.UID = currBank.genUID();

        //now lets generate an ArrayList of accounts for the user
        this.Accounts = new ArrayList<Account>();

        //display user info
        System.out.println("New User: " + fName + " " + lName + "\nUID: " + UID);


    }

    public String getUID() {
        return this.UID;
    }

    public String getFName() {
        return this.fName;
    }

    public double getAcctBalance(int acctIndex) {
        return this.Accounts.get(acctIndex).getBalance();
    }

    public String getAcctUID(int acctIndex) {
        return this.Accounts.get(acctIndex).getAID();
    }

    public void addAccount(Account acctToAdd) {
        //given an account, add it to this USERS ACCOUNT list
        this.Accounts.add(acctToAdd);
    }

    public void addAcctTransaction(int acctIndex, double amount) {
        this.Accounts.get(acctIndex).addTransaction(amount);
    }

    public boolean validatePin(String pin) {
        try {
            //compare the given pin hash with the stored pin hash of this USER
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md5.digest(pin.getBytes()), this.pinCode);
        } catch (NoSuchAlgorithmException e) {
            // error 
            System.err.println("ERROR: NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountsSummary() {
        //print all transactions
        System.out.printf("%s's accounts summary.\n", this.fName);
        //now loop through the accounts.
        for (int i = 0; i < this.Accounts.size(); i++) {
            System.out.printf("%d %s\n", i+1,this.Accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        //returns how many accounts the user has
        return this.Accounts.size();
    }

    public void printAcctTransactionHistory(int accIndex) {
        this.Accounts.get(accIndex).printTransHistory();
    }

}