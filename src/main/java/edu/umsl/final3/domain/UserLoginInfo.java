package edu.umsl.final3.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by genebrowder on 3/10/16.
 */
@Entity
@Table(name="USER_LOGIN_INFO")
public class UserLoginInfo implements Serializable {


    private static final long serialVersionUID = 336542947443965225L;
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="USER_LOGIN_INFO_ID", unique=true, nullable=false)
    @Id
    private long id;

    @Column(name="ACCOUNT_NUMBER")
    private String accountNumber;


    @Column(name="PIN")
    private String pin;

    //Map one to one association between USER_LOGIN_INFO and USER_ID
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    public UserLoginInfo() {
    }

    public UserLoginInfo(String accountNumber, String pin, User user) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
