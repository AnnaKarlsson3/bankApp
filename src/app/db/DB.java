package app.db;

import app.Entities.Bankaccount;
import app.Entities.Transaction;
import app.Entities.User;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.List;

/** A Helper class for interacting with the Database using short-commands */
public abstract class DB {

    public static PreparedStatement prep(String SQLQuery){
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String username, String password){
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM users WHERE username = ? AND password = ?");
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            result = (User)new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }


    public static List<Bankaccount> getaccountsOfUser(long userId){
        List<Bankaccount> result = null;
        PreparedStatement ps = prep("SELECT * FROM bankaccounts WHERE user_id = ? " );
        try {
            ps.setLong(1, userId);

            result = (List<Bankaccount>)(List<?>) new ObjectMapper<>(Bankaccount.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static Bankaccount getCardAccountOfType(String type, long userId){
        Bankaccount result = null;
        PreparedStatement ps = prep("SELECT accountnumber FROM bankaccounts WHERE type = ? AND user_id = ?" );
        try {
            ps.setString(1, type);
            ps.setLong(2, userId);
            result = (Bankaccount) new ObjectMapper<>(Bankaccount.class).mapOne(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static Bankaccount getAccountFromAccountnumber(String accountnumber){
        Bankaccount result = null;
        PreparedStatement ps = prep("SELECT * FROM bankaccounts WHERE accountnumber = ?" );
        try {
            ps.setString(1, accountnumber);
            result = (Bankaccount) new ObjectMapper<>(Bankaccount.class).mapOne(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static Bankaccount getAmountOfAccountNumber(String accountNumber){
        Bankaccount result = null;
        PreparedStatement ps = prep("SELECT amount FROM bankaccounts WHERE accountnumber = ? " );
        try {
            ps.setString(1, accountNumber);
            result = (Bankaccount) new ObjectMapper<>(Bankaccount.class).mapOne(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }


    public static List<Transaction> getTransactionOfBankaccount(String accountnumber, int limit, int offset){
        List<Transaction> result = null;
        PreparedStatement ps = prep("SELECT * FROM transactions WHERE fromaccountnumber = ? OR toaccountnumber = ? LIMIT "+limit + " OFFSET " +offset);
        try {
            ps.setString(1, accountnumber);
            ps.setString(2, accountnumber);
            result = (List<Transaction>)(List<?>) new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }






    public static void transactionToOwnAccounts(String message, Double amount, String fromAccountNumber, String toAccountNumber){
        PreparedStatement ps = prep("INSERT INTO transactions VALUES(NULL, NULL, ?, ?, ?, ?, NULL)");
        try {
            ps.setString(1, message);
            ps.setDouble(2, amount);
            ps.setString(3, fromAccountNumber);
            ps.setString(4, toAccountNumber);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }



    public static void updateAmountInBankaccount(Double amount, String accountNumber){
        PreparedStatement ps = prep("UPDATE bankaccounts SET amount = ? WHERE accountnumber = ?");
        try {
            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);

            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }








    /*
        Example method with default parameters
    public static List<Transaction> getTransactions(int accountId){ return getTransactions(accountId, 0, 10); }
    public static List<Transaction> getTransactions(int accountId, int offset){ return getTransactions(accountId, offset, offset + 10); }
    public static List<Transaction> getTransactions(int accountId, int offset, int limit){
        List<Transaction> result = null;
        PreparedStatement ps = prep("bla bla from transactions WHERE transactionsHistory-id = "+accountId+" LIMIT "+limit" OFFSET "+offset+" );
        try {
            result = (List<Transaction>)new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }
    */


}