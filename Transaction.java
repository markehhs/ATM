import java.util.Date;

public class Transaction {
    private double amount;//amount being transacted (withdraw/deposit)
    private Date time;//date object of when the transaction occurs
    private Account inAcct;//which account the transaction occurs

    public Transaction() {
        //default constructor
    }

    public Transaction(double amount, Account inAcct) {
        this.amount = amount;
        this.inAcct = inAcct;
        this.time = new Date();
    }

    public double getAmount() {
        //return the amount of a transaction
        return this.amount;
    }

    public String getSummaryLine() {
        //this string is a summary of the transaction
        //positive or negative
        if (this.amount >= 0) {
            return String.format("%s : $%.02f", 
            this.time.toString(), this.amount);
        } else {
            return String.format("%s : -$%.02f", 
            this.time.toString(), this.amount);
        }
    }

}