package edu.umsl.final3.domain;
/**
 * Created by genebrowder on 10/22/15.
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="TRANSACTION")
public class Transaction {

    private static final long serialVersionUID = 336542947443965225L;


    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="TRANSACTION_ID")
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @Column(name="TYPE_OF_TRANSACTION")
    private String typeOfTransaction;

    @Column(name="DATE_OF_TRANSACTION")
    private Date dateOfTranaction;

    @Column(name="BALANCE")
    private double balance;

    @Column(name="AMOUNT")
    private double amount;

    public Transaction() {
    }

    public Transaction(Account account, String typeOfTransaction, Date dateOfTranaction, double balance, double amount) {
        this.account = account;
        this.typeOfTransaction = typeOfTransaction;
        this.dateOfTranaction = dateOfTranaction;
        this.balance = balance;
        this.amount = amount;
    }
    public Transaction(long id, Account account, String typeOfTransaction, Date dateOfTranaction, double balance, double amount) {
        this.id = id;
        this.account = account;
        this.typeOfTransaction = typeOfTransaction;
        this.dateOfTranaction = dateOfTranaction;
        this.balance = balance;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public Date getDateOfTranaction() {
        return dateOfTranaction;
    }

    public void setDateOfTranaction(Date dateOfTranaction) {
        this.dateOfTranaction = dateOfTranaction;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
