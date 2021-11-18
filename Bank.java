import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String bankName;//the name of the bank
    private ArrayList<User> Users;//lise of ALL users for the bank
    private ArrayList<Account> Accounts;//list of ALL accounts for the bank

    public Bank() {
        //default constructor
    }

    public Bank(String bankName) {
        this.bankName = bankName;
        this.Users = new ArrayList<User>();//empty list
        this.Accounts = new ArrayList<Account>();//empty lisst
    }
 
    public String genUID() {
        //generate a unique ID for a USER.
        String uid;
        Random r = new Random();
        int length = 9;
        boolean notUnique;
        //while we dont have a unique ID
        //keep trying to generate
        do {
            uid = "";
            for (int i = 0; i < length; i++) {
                //for each char in length
                //generate a random number
                uid += ((Integer)r.nextInt(10)).toString();
            }
            //now check to see if its unique
            notUnique = false;
            for (User u : this.Users) {
                //foreach user in the USERS LIST check to see if this generated
                //UID matches
                if (uid.compareTo(u.getUID()) == 0) {
                    notUnique = true;
                    break;
                }
            }

        } while (notUnique);
        return uid;
    }

    public String genAID() {
        //generate a unique ID for a USER.
        String aid;
        Random r = new Random();
        int length = 9;
        boolean notUnique;
        //while we dont have a unique ID
        //keep trying to generate
        do {
            aid = "";
            for (int i = 0; i < length; i++) {
                //for each char in length
                //generate a random number
                aid += ((Integer)r.nextInt(10)).toString();
            }
            //now check to see if its unique
            notUnique = false;
            for (Account a : this.Accounts) {
                //foreach user in the USERS LIST check to see if this generated
                //UID matches
                if (aid.compareTo(a.getAID()) == 0) {
                    notUnique = true;
                    break;
                }
            }

        } while (notUnique);
        return aid;
    }

    public void addAccount(Account acctToAdd) {
        this.Accounts.add(acctToAdd);
    }

    public String getName() {
        return this.bankName;
    }

    public User addUser(String fName, String lName, String pin) {
        //creates a new USER 
        User newUser = new User(fName, lName, pin, this);
        this.Users.add(newUser);
        //create an account for the new user
        Account newAccount = new Account("Savings", newUser, this);
        //add the account to the holder lists
        //add the account to the bank lists
        newUser.addAccount(newAccount);
        this.Accounts.add(newAccount);

        return newUser;
    }

    public User login(String UID, String pin) {
        //return a User object if the pin is correct
        for (User u : this.Users) {
            //lets search through the list of ALL Users
            if (u.getUID().compareTo(UID) == 0 && u.validatePin(pin)) {
                //correct user and pin so return the user
                return u;
            }
        }
        return null;//user not found and incorrect pin
    }
}