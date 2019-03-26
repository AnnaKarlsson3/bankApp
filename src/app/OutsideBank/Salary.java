package app.OutsideBank;

import app.Entities.Bankaccount;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;

public class Salary implements Runnable{

    String accountnumberUser;
    double oldBalance;
    double salary = 1000;

    @Override
    public void run() {

        while (true){
            System.out.println("starting threadloop");

                getFromDB();
                String message = "SalaryPayment";
                Double newBalance = oldBalance + salary;
                //insert transactionUser
                DB.transactionToOwnAccountsSalary(message, salary, accountnumberUser);
                //update User balance/amount on card
                DB.updateAmountInBankaccount(newBalance, accountnumberUser);

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void getFromDB(){
        //get accountnumberUser of type
        Bankaccount bankaccountnumberUser  = DB.getCardAccountOfType("Salaryaccount", LoginController.getUser().getId());
        accountnumberUser = bankaccountnumberUser.getAccountnumber();

        //get amount of accountnumber
        Bankaccount bankaccount  = DB.getAmountOfAccountNumber(accountnumberUser);
        oldBalance = bankaccount.getAmount();
    }

}//class end

