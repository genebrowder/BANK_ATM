package edu.umsl.final3;

/**
 * Created by genebrowder on 5/12/16.
 */
public class TransactionAsStrings {


    private String typeOfTransaction;


    private String dateOfTranaction;


    private String balance;


    private String amount;

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getDateOfTranaction() {
        return dateOfTranaction;
    }

    public void setDateOfTranaction(String dateOfTranaction) {
        this.dateOfTranaction = dateOfTranaction;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
