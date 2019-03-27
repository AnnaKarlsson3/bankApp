package app.OutsideBank;

import app.Entities.Bankaccount;
import app.db.DB;
import app.login.LoginController;

public class CardPayment {

    public CardPayment(){}

    public void drawMoneyFromCardAccount(){

        double drawAmount = 200.00;

        //accountnumberBank SEB
        String accountnumberBank = "50342343234";

        //get accountType whit limit 1
        Bankaccount getType = DB.getTypeOfUser("Cardaccount", LoginController.getUser().getId(),1);
        String type = getType.getType();

        //get accountnumberUser of type
        Bankaccount bankaccountnumberUser = DB.getCardAccountOfType(type,LoginController.getUser().getId());
        String accountnumberUser = bankaccountnumberUser.getAccountnumber();

        //get amount of accountnumber
        Bankaccount bankaccount  = DB.getAmountOfAccountNumber(accountnumberUser);
        double oldBalance = bankaccount.getAmount();

        if(oldBalance > drawAmount) {
            String message = "CardPayment";
            double newBalance = oldBalance - drawAmount;
            //insert transactionUser
            DB.transactionToOwnAccounts(message, drawAmount, accountnumberUser, accountnumberBank);
            //update User balance/amount on card
            DB.updateAmountInBankaccount(newBalance, accountnumberUser);
        }
        System.out.println("Cardpayment success");
    }

}
