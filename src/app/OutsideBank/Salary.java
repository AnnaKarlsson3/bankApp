package app.OutsideBank;

import app.Entities.Bankaccount;
import app.db.DB;
import app.login.LoginController;

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
                double newBalance = oldBalance + salary;
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
        //get accountType whit limit 1
        Bankaccount getType = DB.getTypeOfUser("Salaryaccount", LoginController.getUser().getId(),1);
        String type = getType.getType();

        //get accountnumberUser of type
        Bankaccount bankaccountnumberUser  = DB.getCardAccountOfType(type, LoginController.getUser().getId());
        accountnumberUser = bankaccountnumberUser.getAccountnumber();

        //get amount of accountnumber
        Bankaccount bankaccount  = DB.getAmountOfAccountNumber(accountnumberUser);
        oldBalance = bankaccount.getAmount();
    }

}

