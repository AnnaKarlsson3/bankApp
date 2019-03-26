package app.OutsideBank;

import app.Entities.Bankaccount;
import app.db.DB;
import app.login.LoginController;

public class CardPayment {

    private Bankaccount bankaccount = null;
    private Bankaccount bankaccountnumberUser = null;

    public CardPayment(){}

    public void drawMoneyFromCardAccount(){

        Double drawAmount = 200.00;

        //accountnumberBank SEB
        String accountnumberBank = "50342343234";

        //get accountnumberUser of type
        bankaccountnumberUser = DB.getCardAccountOfType("Cardaccount",LoginController.getUser().getId());
        String accountnumberUser = bankaccountnumberUser.getAccountnumber();

        //get amount of accountnumber
        bankaccount  = DB.getAmountOfAccountNumber(accountnumberUser);
        double oldBalance = bankaccount.getAmount();

        if(oldBalance > drawAmount) {
            String message = "CardPayment";
            Double newBalance = oldBalance - drawAmount;
            //insert transactionUser
            DB.transactionToOwnAccounts(message, drawAmount, accountnumberUser, accountnumberBank);
            //update User balance/amount on card
            DB.updateAmountInBankaccount(newBalance, accountnumberUser);
        }



        //om den är större






    }
}
